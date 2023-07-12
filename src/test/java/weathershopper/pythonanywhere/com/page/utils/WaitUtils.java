package weathershopper.pythonanywhere.com.page.utils;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static java.io.File.separator;

/**
 * Utility class to handle explicit waits
 */
@Slf4j
public class WaitUtils {

    public static String waitAndFetchText(By locator, Long timeout, WebDriver driver) {
        WebElement webElement = new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        return webElement.getText();
    }

    @Step("Wait for '{timeOut}' Seconds until the PageTitle is: '{expectedTitle}' ")
    public void waitForTitle(String expectedTitle, long timeOut, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.titleIs(expectedTitle));
    }

    public static WebElement waitForElementToBePresent(By locator, Long timeout, WebDriver driver) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitAndSendKeys(By locator, String text, Long timeout, WebDriver driver) {
        WebElement webElement = new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        webElement.clear();
        webElement.sendKeys(text);
    }

    public static void waitAndClick(By locator, Long timeout, WebDriver driver) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .click();
    }

    public static void waitAndDoJavascriptClick(By locator, Long timeout, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator)));
    }

    public static void waitForElementToBeClickable(By locator, Long timeout, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public static void waitForPageRefresh(Long timeout, WebDriver driver) {
        new WebDriverWait(driver, timeout).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static WebElement waitForElementToBeVisibleWithTimeout(By locator, long timeout, WebDriver driver) {
        waitForPageRefresh(timeout, driver);
        try {
            return new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new ElementNotInteractableException(locator.toString() + " could not be found or is not visible");
        }
    }

    public static void takeSnapShot(WebDriver driver) {
        try {
            final String dir = System.getProperty("user.dir");
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(dir + separator + srcFile.getName());
            FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            log.info("Screenshot could not be taken");
        }
    }

}
