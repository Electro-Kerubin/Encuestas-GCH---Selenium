package steps;

import io.cucumber.java.en.Given;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import pages.BasePage;
import pages.ProcesosPorAcPage;


public class ProcesosPorAcSteps extends BasePage {

    public ProcesosPorAcSteps() {
        super(driver);
    }

    final Dotenv dotenv = Dotenv.load();
    ProcesosPorAcPage procesosPorAcPage = new ProcesosPorAcPage();

    @Given("el usuario navega al sitio listado de encuestas")
    public void navegarAlSitio() {
        procesosPorAcPage.navegarProcesosPorAC();
        String titulo = getText("//h1[normalize-space()='Proceso de encuestas por Acción de Capacitación (AC)']");
        Assert.assertEquals(titulo, "Proceso de encuestas por Acción de Capacitación (AC)");
    }

    @Given("el usuario puede crear una encuesta")
    public void crearEncuesta() {
        procesosPorAcPage.crearEncuesta();
    }
}
