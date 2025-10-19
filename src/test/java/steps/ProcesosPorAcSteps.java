package steps;

import io.cucumber.java.en.Given;
import io.github.cdimascio.dotenv.Dotenv;
import pages.BasePage;

public class ProcesosPorAcSteps extends BasePage {

    public ProcesosPorAcSteps() {
        super(driver);
    }

    final Dotenv dotenv = Dotenv.load();

    @Given("el usuario navega al sitio listado de encuestas")
    public void navegarAlSitio() {
        //navigateTo(dotenv.get("GCH_ENCUESTAS") + "/listarencuestasAc");
    }
}
