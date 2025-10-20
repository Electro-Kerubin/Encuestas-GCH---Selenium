package pages;

import io.github.cdimascio.dotenv.Dotenv;
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

    public String crearEncuesta() {

        String nombreEncuesta = utils.generarNormbre("QA");

        navegarProcesosPorAC();

        navigateTo(dotenv.get("GCH_ENCUESTAS") + "/listarencuestasAc");
        clickElement("//button[normalize-space()='Crear Encuesta']");
        clickElement("//img[contains(@src, 'search-02.svg')]");
        write("//input[@name='filtro.nombreAC']", "Ac Cf A Distancia Replica");
        clickElement("//button[normalize-space()='Aplicar']");
        clickElement("//a[normalize-space()='Seleccionar']");
        write("//input[@id='nombreEncuesta']", nombreEncuesta);
        clickElement("//h4[normalize-space()='QR']/ancestor::div[contains(@class, 'card')]");

        return nombreEncuesta;
    }

}
