package steps;

import io.cucumber.java.Before;
import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import pages.BasePage;
import pages.Login;
import utils.SessionManager;

public class Hooks extends BasePage {

    public Hooks() {
        super(driver);
    }

    private static final Dotenv dotenv = Dotenv.load();

    private static final String GCH_ENCUESTAS = dotenv.get("GCH_ENCUESTAS");
    private static final String GCH_URL = dotenv.get("GCH_URL");
    private static final String RUT_USER = dotenv.get("RUT_USER");
    private static final String PASS_USER = dotenv.get("PASS_USER");

    @Before
    public void setUp() {

        String accessToken = SessionManager.readAcessToken();
        String URL = GCH_ENCUESTAS + accessToken;
        System.out.println("URL: " + URL);
        navigateTo(URL);
        try {
            clickElement("//h4[normalize-space()='Seleccionar Cliente Comercial']");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        navigateTo(GCH_ENCUESTAS);
        System.out.println("url actual: " + getUrl());
        System.out.println("validacion: " + !getUrl().equals(GCH_ENCUESTAS + "/"));
        System.out.println("access token:" + accessToken.isBlank());
        if(!getUrl().equals(GCH_ENCUESTAS + "/") || accessToken.isBlank()) {
            navigateTo(GCH_ENCUESTAS);
            Login loginPage =  new Login(driver);
            loginPage.login(RUT_USER, PASS_USER);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if(scenario.isFailed()) {
            scenario.log("Si el escenario falla, consulte la imagen adjunta a este informe.");
            final byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot del error.");
        }
    }
}
