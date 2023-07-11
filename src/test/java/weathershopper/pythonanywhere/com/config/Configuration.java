package weathershopper.pythonanywhere.com.config;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static java.io.File.separator;

/**
 * Configuration class read global properties used in the tests
 */
@Slf4j
public class Configuration {
    private static final String APPLICATION_PROPERTIES_FILENAME = "src" + separator + "test" + separator
            + "resources" + separator + "application.properties";

    private static final String WEB_BROWSER = "web.browser";
    private static final String TEST_ENVIRONMENT_URL = "test.environment.url";
    private static final String WEATHER_FOR_MOISTURIZER = "weather.for.moisturizer";
    private static final String WEATHER_FOR_SUNSCREEN = "weather.for.sunscreen";
    private static final String CUSTOMER_CARD_NUMBER = "customer.card.number";
    private static final String CUSTOMER_CARD_EXPIRY_DATE = "customer.card.expiry.date";
    private static final String CUSTOMER_CARD_CVV_NUMBER = "customer.card.cvv.number";
    private static final String CUSTOMER_ADDRESS_PIN_CODE = "customer.address.pin.code";

    private final Properties appProperties = new Properties();

    public Configuration() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + separator + APPLICATION_PROPERTIES_FILENAME))) {
            appProperties.load(reader);
        } catch (FileNotFoundException exception) {
            log.error("Configuration.properties not found at " + APPLICATION_PROPERTIES_FILENAME, exception);
        } catch (IOException exception) {
            log.error("File could not be loaded", exception);
        }
    }

    public String getWebBrowser() {
        return appProperties.getProperty(WEB_BROWSER, "CHROME");
    }

    public String getTestEnvironmentUrl() {
        return appProperties.getProperty(TEST_ENVIRONMENT_URL);
    }

    public Integer getWeatherForMoisturizer() {
        return Integer.parseInt(appProperties.getProperty(WEATHER_FOR_MOISTURIZER));
    }

    public Integer getWeatherForSunscreen() {
        return Integer.parseInt(appProperties.getProperty(WEATHER_FOR_SUNSCREEN));
    }

    public String getCustomerCardNumber() {
        return appProperties.getProperty(CUSTOMER_CARD_NUMBER);
    }

    public String getCustomerCardExpiryDate() {
        return appProperties.getProperty(CUSTOMER_CARD_EXPIRY_DATE);
    }

    public String getCustomerCardCvvNumber() {
        return appProperties.getProperty(CUSTOMER_CARD_CVV_NUMBER);
    }

    public String getCustomerAddressPinCode() {
        return appProperties.getProperty(CUSTOMER_ADDRESS_PIN_CODE);
    }


}
