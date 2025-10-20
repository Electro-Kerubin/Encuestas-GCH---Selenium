package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    static {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        //options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    Actions actions = new Actions(driver);

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("🧹 Navegador cerrado correctamente");
        }
    }

    public static void navigateTo(String url) {
        driver.get(url);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void waitForUrl(String url) {
        wait.until(ExpectedConditions.urlContains(url));
    }

    private WebElement Find(String locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public boolean elementExists(String locator) {
        return driver.findElements(By.xpath(locator)).size() > 0;
    }

    public void clickElement(String locator) {
        int intentos = 0;
        while (intentos < 5) {
            try {
                By overlayLocator = By.cssSelector("div.overlay");
                wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
            } catch (Exception e) {
                System.out.println("No se detectó overlay o ya había desaparecido.");
            }

            try {
                Find(locator).click();
                return;
            } catch (StaleElementReferenceException stale) {
                System.out.println("reintento: " + intentos + "/5");
                intentos++;
            } catch (Exception e) {
                System.out.println("Error al hacer click: " + e.getMessage());
                throw e;
            }
        }

        throw new RuntimeException("No se pudo hacer click en el elemento: " + locator);

    }

    public void scrollToElement(String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo({ top: 0, behavior: 'smooth' });");
    }

    public boolean elementIsVisible(String locator) {
        return Find(locator).isDisplayed();
    }

    public String getText(String locator) {
        return Find(locator).getText();
    }

    public void write(String locator, String keysToSend) {
        Find(locator).clear();
        Find(locator).sendKeys(keysToSend);
    }

    public void writeByKeys(String keysToSend) {
        actions.sendKeys(keysToSend).perform();
    }

    public void selectFromDropdownByValue(String locator, String value) {

        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));

        Select dropdown = new Select(element);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver1 -> dropdown.getOptions()
                        .stream()
                        .anyMatch(opt -> opt.getAttribute("value").equals(value)));

        dropdown.selectByValue(value);
    }



    public void enterAction() {
        actions.sendKeys(Keys.ENTER).perform();
    }
}


