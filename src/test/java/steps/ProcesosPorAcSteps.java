package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.ProcesosPorAcPage;
import utils.Utils;


public class ProcesosPorAcSteps extends BasePage {

    public ProcesosPorAcSteps() {
        super(driver);
    }

    SoftAssert softAssert = new SoftAssert();
    final Dotenv dotenv = Dotenv.load();
    ProcesosPorAcPage procesosPorAcPage = new ProcesosPorAcPage();
    Utils utils = new Utils();
    private String nombreEncuesta;

    @Given("el usuario navega al sitio listado de encuestas")
    public void navegarAlSitio() {
        procesosPorAcPage.navegarProcesosPorAC();
        String titulo = getText("//h1[normalize-space()='Proceso de encuestas por Acción de Capacitación (AC)']");
        Assert.assertEquals(titulo, "Proceso de encuestas por Acción de Capacitación (AC)");
    }


    @Given("el usuario puede clickear el boton de crear encuesta")
    public void puedeClickearyVerElBotonDeCrearEncuesta() {
        String btn = procesosPorAcPage.botonCrearEncuesta();

        softAssert.assertTrue(elementIsVisible(btn), "El boton de crear encuesta no es visible");
        clickElement(btn);
        softAssert.assertTrue(getUrl().contains("/crearEncuesta"), "No se pudo clickear y ingresar al formulario de crear encuesta");
    }

    @Then("el usuario crea la encuesta")
    public void crearEncuesta() {
        nombreEncuesta = utils.generarNormbre("QA");
        procesosPorAcPage.crearEncuesta(nombreEncuesta);
    }

    @Then("el usuario ve la encuesta creada en la tabla")
    public void elUsuarioPuedeVerEncuestaEnTabla() {
        procesosPorAcPage.navegarProcesosPorAC();
        clickElement("//button[@class='btn btn-kpi']");
        write("//input[@id='encuestaNombre']", nombreEncuesta);
        clickElement("//button[normalize-space()='Aplicar']");

        String xpathTabla = String.format("//td[normalize-space()='%s']",  nombreEncuesta);
        System.out.println("Se encuentraen la tabla? " + xpathTabla);
        Assert.assertTrue(elementExists(xpathTabla), "No se ha creado correctamente");
    }


}
