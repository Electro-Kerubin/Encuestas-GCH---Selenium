package utils;

import com.google.gson.Gson;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class SessionManager {
    private static final String COOKIE_FILE_PATH = "cookies.json";
    private static final Gson gson = new Gson();

    public static void saveCookies(WebDriver driver) {
        try (Writer writer = new FileWriter(COOKIE_FILE_PATH)) {
            Set<Cookie> cookies = driver.manage().getCookies();
            gson.toJson(cookies, writer);
            System.out.println("Cookies guardadas correctamente en cookies.json");
        } catch (IOException e) {
            System.err.println("Error al guardar cookies: " + e.getMessage());
        }
    }

    public static void loadCookies(WebDriver driver) {
        File file = new File(COOKIE_FILE_PATH);
        if (!file.exists()) {
            System.out.println("No se encontró cookies.json. Se realizará login.");
            return;
        }

        try (Reader reader = new FileReader(file)) {
            Type cookieSetType = new TypeToken<HashSet<Cookie>>() {}.getType();
            Set<Cookie> cookies = gson.fromJson(reader, cookieSetType);

            for (Cookie cookie : cookies) {
                try {
                    driver.manage().addCookie(cookie);
                } catch (Exception ex) {
                    System.err.println("Error agregando cookie: " + cookie.getName());
                }
            }

            System.out.println("Cookies cargadas desde cookies.json");
        } catch (IOException e) {
            System.err.println("Error al cargar cookies: " + e.getMessage());
        }
    }

    public static boolean cookiesExist() {
        return new File(COOKIE_FILE_PATH).exists();
    }

    public static boolean cookiesExpired() {
        File file = new File(COOKIE_FILE_PATH);
        if (!file.exists()) return true;
        long age = System.currentTimeMillis() - file.lastModified();
        long twelveHours = 12 * 60 * 60 * 1000; // 12 horas
        return age > twelveHours;
    }
}
