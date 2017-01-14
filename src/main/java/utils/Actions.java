package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.*;

/**
 * Created by Adam on 2016-12-18.
 */
public abstract class Actions {

    private static WebDriverWait getWait() {
        return new WebDriverWait(Base.getInstance(), Constant.TIMEOUT_IN_SECONDS);
    }

    /**
     * Waits for specified url
     *
     * @param url to be
     */
    public static void waitForUrlToBe(String url) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " " + url);
            getWait().until(ExpectedConditions.urlToBe(url));
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Waits for element to be visible for Constant.TIMEOUT_IN_SECONDS
     *
     * @param element WebElement
     */
    public static void waitForVisible(WebElement element) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until(ExpectedConditions.visibilityOf(element));
            TestReport.addLog(LogStatus.INFO, "Element " + Helper.getElementLocator(element) + " is visible");
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + " is hidden or not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Waits for element to be hidden
     *
     * @param element WebElement
     */
    public static void waitForHidden(WebElement element) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
            TestReport.addLog(LogStatus.INFO, "Element " + Helper.getElementLocator(element) + " is hidden");
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "is visible");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        } catch (NoSuchElementException e1) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "not found");
            TestReport.addLog(LogStatus.INFO, e1);
            throw e1;
        }
    }

    /**
     * Waits for element to be clickable
     *
     * @param element WebElement
     */
    public static void waitForClickable(WebElement element) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until(ExpectedConditions.elementToBeClickable(element));
            TestReport.addLog(LogStatus.INFO, "Element " + Helper.getElementLocator(element) + " is clickable");
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "is not clickable or not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Waits for text presence in element
     *
     * @param element WebElement
     * @param text    to be present
     */
    public static void waitForTextOnElement(WebElement element, String text) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
            TestReport.addLog(LogStatus.INFO, "Text \"" + text + "\" present on " + Helper.getElementLocator(element));
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Text \"" + text + "\" not present on " + Helper.getElementLocator(element));
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Scrolls to element on page
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " " + Helper.getElementLocator(element));
        if (Base.getInstance() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) Base.getInstance())
                    .executeScript("arguments[0].scrollIntoView(true);", element);
        }
    }

    /**
     * Click on element
     *
     * @param element WebElement
     */
    public static void click(WebElement element) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            element.click();
            TestReport.addLog(LogStatus.INFO, "Clicked on element " + Helper.getElementLocator(element));
        } catch (NoSuchElementException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Sends charSequence to an element
     *
     * @param element      WebElement
     * @param charSequence to be send
     */
    public static void sendKeys(WebElement element, CharSequence charSequence) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " to element " + Helper.getElementLocator(element));
            element.sendKeys(charSequence);
            TestReport.addLog(LogStatus.INFO, "Send \"" + charSequence + "\" to element" + Helper.getElementLocator(element));
        } catch (NoSuchElementException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Opens page using provided url
     *
     * @param url of page to be opened
     */
    public static void openPage(String url) {
        Base.getInstance().manage().timeouts().pageLoadTimeout(Constant.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        Base.getInstance().get(url);
        waitForUrlToBe(url);
        TestReport.addLog(LogStatus.INFO, "Opened page " + url);
    }
}
