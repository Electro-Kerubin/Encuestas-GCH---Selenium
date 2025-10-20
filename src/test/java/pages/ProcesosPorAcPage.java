package pages;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Utils;


public class ProcesosPorAcPage extends BasePage {

    public ProcesosPorAcPage() {
        super(driver);
    }

    final Dotenv dotenv = Dotenv.load();
    Utils utils = new Utils();

    public void navegarProcesosPorAC() {
        navigateTo(dotenv.get("GCH_ENCUESTAS") + "/listarencuestasAc");
    }

    public void guardarYContinuarBtn() {
        try {
            By overlayLocator = By.cssSelector("div.overlay");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        } catch (Exception e) {
            System.out.println("No se detectó overlay o ya había desaparecido.");
        }
        scrollToElement("//button[normalize-space()='Guardar y Continuar']");
        clickElement("//button[normalize-space()='Guardar y Continuar']");
        scrollToTop();
    }

    public String botonCrearEncuesta() {
        navegarProcesosPorAC();
        String crearEncuestaBtn = "//button[normalize-space()='Crear Encuesta']";
        return crearEncuestaBtn;
    }

    public String crearEncuesta(String nombreEncuesta) {

        clickElement("//img[contains(@src, 'search-02.svg')]");
        write("//input[@name='filtro.nombreAC']", "Ac Cf A Distancia Replica");
        clickElement("//button[normalize-space()='Aplicar']");
        clickElement("//a[normalize-space()='Seleccionar']");
        write("//input[@id='nombreEncuesta']", nombreEncuesta);
        clickElement("//h4[normalize-space()='QR']/ancestor::div[contains(@class, 'card')]");
        guardarYContinuarBtn();
        clickElement("//label[.//div[contains(text(), 'Abierto (sin ventanas')]]");
        selectFromDropdownByValue("//select[contains(@class,'form-control')]", "62");
        clickElement("//button[normalize-space()='Cargar plantilla']");
        write("//input[@id='mensajeInicio']", "Mensaje inicio TEST");
        write("//input[@id='mensajeCierre']", "Mensaje cierre TEST");
        write("//input[@id='linkRedirect']", "google.com");
        guardarYContinuarBtn();
        guardarYContinuarBtn();

        return nombreEncuesta;
    }

}
