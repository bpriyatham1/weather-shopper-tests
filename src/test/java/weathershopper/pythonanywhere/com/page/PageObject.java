package weathershopper.pythonanywhere.com.page;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import weathershopper.pythonanywhere.com.config.Configuration;

import java.time.Duration;

import static weathershopper.pythonanywhere.com.page.utils.WaitUtils.takeSnapShot;
import static weathershopper.pythonanywhere.com.page.utils.WaitUtils.waitForPageRefresh;


/**
 * Parent class for all pages within the page object model
 */
@Getter
@Setter
@Slf4j
public class PageObject {
    public static final String HTTPS = "https://";

    protected static final Long TIMEOUT = 20L;

    protected WebDriver driver;
    private Configuration configuration;

    public PageObject(WebDriver driver) {
        try {
            this.driver = driver;
        } catch (Exception e) {
            log.error("WebDriver couldn't be found", e);
        }
    }

    public Long getTimeout() {
        return TIMEOUT;
    }


    protected void waitForElementToBeVisibleWithTimeout(By elementBy) {
        waitForElementToBeVisible(elementBy, getTimeout(), driver);
    }

    public static void waitForElementToBeVisible(By locator, long timeout, WebDriver driver) {
        waitForPageRefresh(timeout, driver);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            takeSnapShot(driver);
            throw new ElementNotInteractableException(locator.toString() + " could not be found or is not visible");
        }
    }

    protected void waitForPageTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PageObject.TIMEOUT));
        wait.until(ExpectedConditions.titleIs(title));
    }

}
