package weathershopper.pythonanywhere.com.tests;

import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import weathershopper.pythonanywhere.com.utils.TestListener;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;
import static weathershopper.pythonanywhere.com.staticdata.ReportingEpics.*;
import static weathershopper.pythonanywhere.com.staticdata.ReportingFeatures.*;
import static weathershopper.pythonanywhere.com.staticdata.TestTags.HEALTH_CHECK;
import static weathershopper.pythonanywhere.com.staticdata.TestTags.REGRESSION;

@Listeners({TestListener.class})
public class RegressionTest extends BaseTest {

    private static final String POPUP_TEXT = "Shop for moisturizers if the weather is below 19 degrees. Shop for suncreens if the weather is above 34 degrees.";

    @Test(groups = {HEALTH_CHECK}, description = "Verifies that api service answer with status code 200")
    @Story("Perform healthCheck on api service")
    @Features({@Feature(API)})
    @Severity(SeverityLevel.BLOCKER)
    void apiHealthcheckTest() {
        String testEnvironmentUrl = config.getTestEnvironmentUrl();
        assertThat(getHealth(testEnvironmentUrl)).as("Health Check of weather shopper api").isEqualTo(HttpStatus.SC_OK);
    }


    @Test(groups = {REGRESSION}, priority = 1, description = "Description: Perform Weather shopper task")
    @Story("Story: place an order on Weather shopper Website successfully... ")
    @Epics({@Epic(CHECKOUT), @Epic(PAYMENT), @Epic(PLACE_ORDER)})
    @Features({@Feature(DESKTOP), @Feature(MOBILE)})
    @Severity(SeverityLevel.BLOCKER)
    void performWeatherShopperTaskTest() throws MalformedURLException {
        setup();
        // Homepage title assertion
        String actualTitle = weatherPage.navigateToHomePageAndFetchTitle();
        String expectedTitle = "Current Temperature";
        assertEquals(actualTitle, expectedTitle);
        // Info popup text assertion
        String actualText = weatherPage.fetchPopUpText();
        assertEquals(actualText, POPUP_TEXT);
        // Perform task based on condition
        weatherPage.performTaskBasedOnTextDisplayed();
        // Validate Payment Finish page
        String actualPaymentInfoText = weatherPage.fetchTextFromLabel(weatherPage.getPaymentInfoLabel());
        String expectedPaymentInfoText = "PAYMENT SUCCESS";
        assertThat(actualPaymentInfoText).as("PaymentMessage Assertion").isEqualTo(expectedPaymentInfoText);
        String actualPaymentMessageText = weatherPage.fetchTextFromLabel(weatherPage.getPaymentMessageLabel());
        String expectedPaymentMessageText = "Your payment was successful. You should receive a follow-up call from our sales team.";
        assertThat(actualPaymentMessageText).as("PaymentMessage Assertion").isEqualTo(expectedPaymentMessageText);
        tearDown();
    }

}
