package weathershopper.pythonanywhere.com.tests;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import weathershopper.pythonanywhere.com.page.WeatherPage;
import weathershopper.pythonanywhere.com.type.BrowserType;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public WebDriver driver;
    public WeatherPage weatherPage;


    public WebDriver getDriver() {
        return driver;
    }

    @Step("TestSetUp: Open new Chrome Browser and Open ClickDoc WeatherPage... ")
    void setup() throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
//        driver = new ChromeDriver();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME.toString());

        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        weatherPage = new WeatherPage(driver);
    }

    @Step("TestCleanUp: Close Web Browser... ")
    void tearDown() {
        driver.close();
        driver.quit();
    }

    @Step("Performs request on service health url")
    Integer getHealth(String url) {
        return given().when().get(url).then().extract().response().getStatusCode();
    }
//
//    private WebDriver initializeRemoteWebDriver(String browser, String gridUrl) throws MalformedURLException {
//        WebDriver driver;
//        switch (BrowserType.valueOf(browser)) {
//            case CHROME:
//                driver = initializeRemoteChromeDriver(gridUrl);
//                break;
//            case FIREFOX:
//                driver = initializeRemoteFirefoxDriver(gridUrl);
//                break;
//            case SAFARI:
//                driver = initializeRemoteSafariDriver(gridUrl);
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: [" + browser + "]");
//        }
//        return driver;
//    }
//
//    private WebDriver initializeRemoteSafariDriver(String gridUrl) throws MalformedURLException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        SafariOptions safariOptions = new SafariOptions();
//        safariOptions.setUseTechnologyPreview(true);
//        safariOptions.setAutomaticInspection(true);
//        safariOptions.setAutomaticProfiling(true);
//
//        capabilities.setJavascriptEnabled(true);
//        capabilities.setCapability("ensureCleanSession", true);
//        capabilities.setCapability("disable-logging", true);
//        capabilities.setCapability("marionette", true);
//        capabilities.setCapability("acceptInsecureCerts", true);
//        capabilities.setCapability("acceptSslCerts", true);
//        capabilities.setPlatform(Platform.MAC);
//
//        safariOptions.merge(capabilities);
//        return new RemoteWebDriver(new URL(gridUrl), safariOptions);
//    }

//    private WebDriver initializeRemoteFirefoxDriver(String gridUrl) throws MalformedURLException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//
//        capabilities.setJavascriptEnabled(true);
//        capabilities.setCapability("ensureCleanSession", true);
//        capabilities.setCapability("disable-logging", true);
//        capabilities.setCapability("marionette", true);
//        capabilities.setCapability("acceptInsecureCerts", true);
//        capabilities.setCapability("acceptSslCerts", true);
//        capabilities.setPlatform(Platform.LINUX);
//
//        firefoxOptions.merge(capabilities);
//        return new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
//    }
//
//    private RemoteWebDriver initializeRemoteChromeDriver(String gridUrl) throws MalformedURLException {
//        final DesiredCapabilities capabilities = new DesiredCapabilities();
//
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--incognito");
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--disable-extensions");
//        chromeOptions.addArguments("--verbose");
//        chromeOptions.addArguments("--disable-dev-shm-usage");
//        chromeOptions.setHeadless(true);
//        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//        capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors", "--disable-infobars", "--enable-strict-powerful-feature-restrictions"));
//        capabilities.setCapability("nativeEvents", true);
//        capabilities.setCapability("chrome.nativeEvents", true);
//        capabilities.setCapability("disable-logging", true);
//        capabilities.setJavascriptEnabled(true);
//        capabilities.setCapability("acceptInsecureCerts", true);
//        capabilities.setCapability("acceptSslCerts", true);
//        capabilities.setPlatform(Platform.LINUX);
//        chromeOptions.merge(capabilities);
//        HttpClient.Factory factory = new OkHttpClient.Factory();
//        HttpCommandExecutor executor = new HttpCommandExecutor(EMPTY_MAP, new URL(gridUrl), factory);
//        return new RemoteWebDriver(executor, capabilities);
//    }
//
//    private WebDriver initializeWebDriver(String browser) throws Exception {
//        DriverManagerType driverManagerType = DriverManagerType.valueOf(browser);
//        WebDriverManager.getInstance(driverManagerType).setup();
//        Class<?> driverClass = Class.forName(driverManagerType.browserClass());
//        return (WebDriver) driverClass.getDeclaredConstructor().newInstance();
//    }

}