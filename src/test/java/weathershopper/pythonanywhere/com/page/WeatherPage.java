package weathershopper.pythonanywhere.com.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import weathershopper.pythonanywhere.com.config.Configuration;

import static weathershopper.pythonanywhere.com.page.utils.WaitUtils.waitAndClick;
import static weathershopper.pythonanywhere.com.page.utils.WaitUtils.waitAndFetchText;
import static weathershopper.pythonanywhere.com.page.utils.WaitUtils.waitAndSendKeys;
import static weathershopper.pythonanywhere.com.utils.RandomUtils.generateRandomEmail;

@Slf4j
public class WeatherPage extends PageObject {

    public WeatherPage(WebDriver driver) {
        super(driver);
    }

    private static final Configuration config = new Configuration();

    // home page locators
    By infoButton = By.cssSelector(".octicon.octicon-info");
    By popUpText = By.cssSelector(".popover-body");
    By temperature = By.cssSelector("#temperature");
    By buyMoisturizersLink = By.linkText("Buy moisturizers");
    By buySunscreensLink = By.linkText("Buy sunscreens");


    // product details page locators
    By addToCartButton = By.xpath("//button[@class='btn btn-primary']");
    By shoppingCartButton = By.cssSelector(".thin-text.nav-link");

    // cart page locators
    By payWithCardButton = By.cssSelector("button[type='submit'] span");
    By iframeLabel = By.cssSelector("iframe[name='stripe_checkout_app']");
    By emailField = By.id("email");
    By cardNumberField = By.id("card_number");
    By cardExpiryDateField = By.id("cc-exp");
    By cardCvvNumber = By.id("cc-csc");
    By zipCodeField = By.id("billing-zip");
    By submitButton = By.id("submitButton");
    By paymentInfoLabel = By.cssSelector("div[class='row justify-content-center'] h2");
    By paymentMessageLabel = By.cssSelector(".text-justify");

    @Step("Wait For weather Shopper WeatherPage to be loaded and return Homepage title")
    public String navigateToHomePageAndFetchTitle() {
        driver.get(config.getTestEnvironmentUrl());
        return driver.getTitle();
    }

    @Step("Fetch text from popup and validate text...")
    public String fetchPopUpText() {
        waitAndClick(infoButton, TIMEOUT, driver);
        return waitAndFetchText(popUpText, TIMEOUT, driver);
    }

    @Step("Shopping for moisturizers if the weather is below 19 degrees and Shopping for sunscreens if the weather is above 34 degrees.")
    public void performTaskBasedOnTextDisplayed() {
        waitForElementToBeVisibleWithTimeout(temperature);
        String temperatureInCelsius = driver.findElement(temperature).getText();
        String temperatureWithoutCelsius = temperatureInCelsius.replaceAll("[^0-9]", "");
        if (Integer.parseInt(temperatureWithoutCelsius) < config.getWeatherForMoisturizer()) {
            waitAndClick(buyMoisturizersLink, TIMEOUT, driver);
            buyMoisturizer();
        } else if (Integer.parseInt(temperatureWithoutCelsius) > config.getWeatherForSunscreen()) {
            waitAndClick(buySunscreensLink, TIMEOUT, driver);
            buySunscreen();
        } else if (Integer.parseInt(temperatureWithoutCelsius) >= config.getWeatherForMoisturizer() && Integer.parseInt(temperatureWithoutCelsius) <= config.getWeatherForSunscreen()) {
            log.info("Not performing any task as the temperature is > 19 Degrees and less than 33 Degrees");
        }
    }

    @Step("Place order of Moisturizer item")
    public void buyMoisturizer() {
        waitForPageTitle("The Best Moisturizers in the World!");
        placeOrder();
    }

    @Step("Place order of Sunscreen item")
    private void buySunscreen() {
        waitForPageTitle("The Best Sunscreens in the World!");
        placeOrder();
    }

    @Step("Place order based on weather condition")
    private void placeOrder() {
        waitAndClick(addToCartButton, TIMEOUT, driver);
        waitForElementToBeVisibleWithTimeout(shoppingCartButton);
        assert waitAndFetchText(shoppingCartButton, TIMEOUT, driver).contains("1 item(s)");
        waitAndClick(shoppingCartButton, TIMEOUT, driver);
        waitAndClick(payWithCardButton, TIMEOUT, driver);

        payWithCreditCard();
        // Complete the Payment
        waitAndClick(submitButton, TIMEOUT, driver);
        driver.switchTo().defaultContent();
        waitForPageTitle("Confirmation");
    }

    @Step("Entering the credit card and Address information and initiate payment")
    private void payWithCreditCard() {
        waitForElementToBeVisibleWithTimeout(iframeLabel);
        WebElement iframeWebElement = driver.findElement(iframeLabel);
        driver.switchTo().frame(iframeWebElement);
        waitAndSendKeys(emailField, generateRandomEmail(), TIMEOUT, driver);
        sendCharacters(cardNumberField, config.getCustomerCardNumber().toCharArray());
        sendCharacters(cardExpiryDateField, config.getCustomerCardExpiryDate().toCharArray());
        waitAndSendKeys(cardCvvNumber, config.getCustomerCardCvvNumber(), TIMEOUT, driver);
        if (driver.findElement(zipCodeField).isDisplayed()) {
            waitAndSendKeys(zipCodeField, config.getCustomerAddressPinCode(), TIMEOUT, driver);
        }
    }


    private void sendCharacters(By webElement, char[] charArray) {
        for (char character : charArray) {
            driver.findElement(webElement).sendKeys(String.valueOf(character), Keys.END);
        }
    }

    public String fetchTextFromLabel(By webElement) {
        waitForElementToBeVisibleWithTimeout(webElement);
        return driver.findElement(webElement).getText();
    }

    public By getPaymentInfoLabel() {
        return paymentInfoLabel;
    }

    public By getPaymentMessageLabel() {
        return paymentMessageLabel;
    }

}
