package steps;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
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
    private static final String RUT_USER = dotenv.get("RUT_USER");
    private static final String PASS_USER = dotenv.get("PASS_USER");

    @Before
    public void setUp() {
        navigateTo(GCH_ENCUESTAS);

        if (SessionManager.cookiesExist() && !SessionManager.cookiesExist()) {
            SessionManager.loadCookies(driver);
            driver.navigate().refresh();
        } else {
            Login loginPage =  new Login(driver);
            loginPage.login(RUT_USER, PASS_USER);
            SessionManager.saveCookies(driver);
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
