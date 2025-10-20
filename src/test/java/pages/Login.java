package pages;

import io.github.cdimascio.dotenv.Dotenv;
import utils.SessionManager;

public class Login extends BasePage {

    //private WebDriver driver;

    public Login() {
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

        ingresarClienteComercial("Gasco");
    }

    public void ingresarClienteComercial(String clienteComercial) {
        clickElement("//span[contains(@class,'k-input')]");
        writeByKeys(clienteComercial);
        enterAction();
        clickElement("//ngb-modal-window//button[normalize-space()='Aceptar']");
        waitForUrl("/indicadores/home");
    }

    private static final Dotenv dotenv = Dotenv.load();

    private static final String GCH_ENCUESTAS = dotenv.get("GCH_ENCUESTAS");
    private static final String GCH_URL = dotenv.get("GCH_URL");
    private static final String RUT_USER = dotenv.get("RUT_USER");
    private static final String PASS_USER = dotenv.get("PASS_USER");

    public void loginGCH() {
        pages.Login loginPage =  new pages.Login();
        String accessToken = SessionManager.readAcessToken();
        String URL = GCH_ENCUESTAS + accessToken;
        System.out.println("URL: " + URL);
        navigateTo(URL);
        try {
            clickElement("//h4[normalize-space()='Seleccionar Cliente Comercial']");
            loginPage.ingresarClienteComercial("Gasco");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        navigateTo(GCH_ENCUESTAS);
        System.out.println("url actual: " + getUrl());
        System.out.println("validacion: " + !getUrl().equals(GCH_ENCUESTAS + "/"));
        System.out.println("access token:" + accessToken.isBlank());

        if(!getUrl().equals(GCH_ENCUESTAS + "/") || accessToken.isBlank()) {
            navigateTo(GCH_ENCUESTAS);
            loginPage.login(RUT_USER, PASS_USER);
        }
    }


}
