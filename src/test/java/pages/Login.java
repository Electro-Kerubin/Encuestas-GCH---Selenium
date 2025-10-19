package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.SessionManager;

import java.util.Set;

public class Login extends BasePage {

    //private WebDriver driver;

    public Login(WebDriver driver) {
        super(driver);
    }

    public void login(String RUT_USER, String PASS_USER) {

        final Dotenv dotenv = Dotenv.load();

        String currentUrl;
        String finalUrl;

        write("//input[@id='rut']", RUT_USER);
        write("//input[@id='password']", PASS_USER);
        clickElement("//input[@value='Iniciar Sesión']");

        clickElement("//h4[normalize-space()='Seleccionar Cliente Comercial']");
        currentUrl = getUrl();
        SessionManager.saveAccessToken(driver);
        finalUrl = currentUrl.replace(dotenv.get("GCH_URL"), dotenv.get("GCH_ENCUESTAS"));
        System.out.println("url gch: " + dotenv.get("GCH_URL"));
        System.out.println("url encuestas gch: " + dotenv.get("GCH_ENCUESTAS"));
        System.out.println("url final: " + finalUrl);
        navigateTo(finalUrl);

        clickElement("//span[contains(@class,'k-input')]");
        writeByKeys("Gasco");
        enterAction();
        clickElement("//ngb-modal-window//button[normalize-space()='Aceptar']");
        waitForUrl("/indicadores/home");

    }
}
