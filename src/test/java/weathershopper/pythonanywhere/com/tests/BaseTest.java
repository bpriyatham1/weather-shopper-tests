package weathershopper.pythonanywhere.com.tests;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import weathershopper.pythonanywhere.com.config.Configuration;
import weathershopper.pythonanywhere.com.page.WeatherPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public WebDriver driver;
    public WeatherPage weatherPage;
    protected static final Configuration config = new Configuration();


    public WebDriver getDriver() {
        return driver;
    }

    @Step("TestSetUp: Setup Chrome Browser and Open Weather shopper website... ")
    void setup() throws MalformedURLException {
        /* Only for debugging purposes:
        For Local Test Execution enable below lines of code and comment code from line 34 to line 40
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(); */
        ChromeOptions options = new ChromeOptions();
        options.addArguments("no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--single-process");
        options.addArguments("--disable-dev-shm-usage");
        driver = new RemoteWebDriver(new URL(config.getSeleniumGridUrl()), options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        weatherPage = new WeatherPage(driver);
    }

    @Step("TestCleanUp: Close Web Browser... ")
    void tearDown() {
        driver.quit();
    }

    @Step("Performs request on service health url")
    Integer getHealth(String url) {
        return given().when().get(url).then().extract().response().getStatusCode();
    }

}