package utils;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class SessionManager {

    private static final Dotenv dotenv = Dotenv.load();

    private static final Path filePath = Path.of("accessToken.txt");
    private static final String GCH_URL = dotenv.get("GCH_URL");
    private static final Gson gson = new Gson();

    public static void saveAccessToken(WebDriver driver) {
        try {
            String currentUrl = driver.getCurrentUrl();
            String accessToken = currentUrl.replace( GCH_URL, "");
            //System.out.println( "accesstoken : " + accessToken );
            Files.write(filePath, accessToken.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.err.println("Error al guardar accessToken: " + e.getMessage());
        }
    }

    public static String readAcessToken() {
        try {
            Path filePath = Path.of("accessToken.txt");

            String token = Files.readString(filePath);

            System.out.println("AccessToken leido: " + token);
            return token;
        } catch (Exception e) {
            System.err.println("Error al leer accessToken: " + e.getMessage());
            return null;
        }
    }


}
