package utils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Adam on 2016-12-18.
 */
public abstract class Actions {

    protected static WebDriverWait getWait() {
        return new WebDriverWait(Base.getInstance(), Constant.TIMEOUT_IN_SECONDS);
    }

    /**
     * Opens page using provided url
     *
     * @param url of page to be opened
     */
    public static void openPage(String url) {
        Base.getInstance().manage().timeouts().pageLoadTimeout(Constant.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        Base.getInstance().get(url);
        TestReport.addLog(LogStatus.INFO, "Opened page " + url);
    }

    /**
     * Waits for page title to contains string
     *
     * @param pageTitle string to contain in page title
     */
    public static void waitForPageTitleContain(String pageTitle) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " " + pageTitle);
            getWait().until(ExpectedConditions.titleContains(pageTitle));
            TestReport.addLog(LogStatus.INFO, "Page title contains: " + pageTitle);
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Page title is: " + Base.getInstance().getTitle() + " but should contain: " + pageTitle);
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
            Helper.highlight(element);
            TestReport.addLog(LogStatus.INFO, "Element " + Helper.getElementLocator(element) + " is visible");
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + " is hidden or not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Waits for element to be not visible
     *
     * @param element WebElement
     */
    public static void waitForInvisible(WebElement element) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until((WebDriver d) -> {
                try {
                    if (!element.isDisplayed()) {
                        return true;
                    }
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                } catch (StaleElementReferenceException e) {
                    return true;
                }
            });
            TestReport.addLog(LogStatus.INFO, "Element " + Helper.getElementLocator(element) + " is not visible");
        } catch (
                TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "is visible");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        } catch (
                NoSuchElementException e1)

        {
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
     * @param element              WebElement
     * @param text                 to be present
     * @param throwErrorIfNotFound if true, throws error when text not found
     */
    public static void waitForText(WebElement element, String text, boolean throwErrorIfNotFound) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
            Helper.highlight(element);
            TestReport.addLog(LogStatus.INFO, "Text \"" + text + "\" present on element" + Helper.getElementLocator(element));
        } catch (TimeoutException e) {
            try {
                TestReport.addLog(LogStatus.ERROR, "Text \"" + text + "\" not present on element" + Helper.getElementLocator(element) + ", found text " + element.getText());
                Helper.highlight(element);
            } catch (NoSuchElementException e1) {
                TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + " not found");
                if (throwErrorIfNotFound) {
                    TestReport.addLog(LogStatus.INFO, e1);
                    throw e1;
                }
            }
            if (throwErrorIfNotFound) {
                TestReport.addLog(LogStatus.INFO, e);
                throw e;
            }
        }
    }

    /**
     * Waits for text presence in element
     *
     * @param element WebElement
     * @param text    to be present
     */
    public static void waitForText(WebElement element, String text) {
        waitForText(element, text, true);
    }

    /**
     * Waits for text case insensitive and trimmed to be found on WebElement
     *
     * @param element              WebElement
     * @param text                 trimmed and case insensitive text to be found
     * @param throwErrorIfNotFound if true, throws error when text not found
     */
    public static void waitForTextCaseInsensitive(WebElement element, String text, boolean throwErrorIfNotFound) {
        waitForVisible(element);
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until((WebDriver d) -> {
                try {
                    Helper.highlight(element);
                    if (element.getText().trim().toLowerCase().contains(text.trim().toLowerCase())) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (StaleElementReferenceException e) {
                    return true;
                }
            });
            Helper.highlight(element);
            TestReport.addLog(LogStatus.INFO, "Text \"" + text + "\" present on element" + Helper.getElementLocator(element));
        } catch (TimeoutException e) {
            try {
                TestReport.addLog(LogStatus.ERROR, "Text \"" + text + "\" not present on element" + Helper.getElementLocator(element) + ", found text " + element.getText());
                Helper.highlight(element);
            } catch (NoSuchElementException e1) {
                TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + " not found");
                if (throwErrorIfNotFound) {
                    TestReport.addLog(LogStatus.INFO, e1);
                    throw e1;
                }
            }
            if (throwErrorIfNotFound) {
                TestReport.addLog(LogStatus.INFO, e);
                throw e;
            }
        }
    }

    /**
     * Waits for text case insensitive and trimmed to be found on WebElement
     *
     * @param element WebElement
     * @param text    trimmed and case insensitive text to be found
     */
    public static void waitForTextCaseInsensitive(WebElement element, String text) {
        waitForTextCaseInsensitive(element, text, true);
    }

    /**
     * Waits for value presence in element
     *
     * @param element              WebElement
     * @param value                value to be present
     * @param throwErrorIfNotFound if true, throws error when text not found
     */
    public static void waitForValue(WebElement element, String value, boolean throwErrorIfNotFound) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            getWait().until(ExpectedConditions.textToBePresentInElementValue(element, value));
            Helper.highlight(element);
            TestReport.addLog(LogStatus.INFO, "Value \"" + value + "\" present on element " + Helper.getElementLocator(element));
        } catch (TimeoutException e) {
            try {
                TestReport.addLog(LogStatus.ERROR, "Value \"" + value + "\" not present on element " + Helper.getElementLocator(element) + ", text found: " + element.getAttribute("value"));
                Helper.highlight(element);
            } catch (NoSuchElementException e1) {
                TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + " not found");
                if (throwErrorIfNotFound) {
                    TestReport.addLog(LogStatus.INFO, e1);
                    throw e1;
                }
            }
            if (throwErrorIfNotFound) {
                TestReport.addLog(LogStatus.INFO, e);
                throw e;
            }
        }
    }

    /**
     * Waits for value presence in element
     *
     * @param element WebElement
     * @param value   value to be present
     */
    public static void waitForValue(WebElement element, String value) {
        waitForValue(element, value, true);
    }

    /**
     * Scrolls to element on page
     *
     * @param element webElement
     */
    public static void scrollToElement(WebElement element) {
        TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " " + Helper.getElementLocator(element));
        getWait().until((WebDriver d) -> {
            try {
                if (Base.getInstance() instanceof JavascriptExecutor) {
                    ((JavascriptExecutor) Base.getInstance()).executeScript("arguments[0].scrollIntoView(true);", element);
                    return true;
                }
                return false;
            } catch (NoSuchElementException e) {
                return true;
            } catch (StaleElementReferenceException e) {
                return true;
            }
        });
    }

    /**
     * Click on element
     *
     * @param element WebElement
     */
    public static void click(WebElement element) {
        scrollToElement(element);
        waitForClickable(element);
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " element " + Helper.getElementLocator(element));
            element.click();
            Helper.highlight(element);
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
        waitForVisible(element);
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " to element " + Helper.getElementLocator(element));
            getWait().until((WebDriver d) -> {
                try {
                    element.clear();
                    element.sendKeys(charSequence);
                    return true;
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
            TestReport.addLog(LogStatus.INFO, "Send \"" + charSequence + "\" to element" + Helper.getElementLocator(element));
        } catch (NoSuchElementException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Selects element from dropdown list based on visible text
     *
     * @param element WebElement
     * @param text    to select
     */
    public static void selectByVisibleText(WebElement element, String text) {
        waitForVisible(element);
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " to element " + Helper.getElementLocator(element));
            Helper.highlight(element);
            getWait().until((WebDriver d) -> {
                try {
                    new Select(element).selectByVisibleText(text);
                    return true;
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
            TestReport.addLog(LogStatus.INFO, "Selected element " + Helper.getElementLocator(element) + " by text " + text);
        } catch (NoSuchElementException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(element) + "not found");
            TestReport.addLog(LogStatus.ERROR, e);
            throw e;
        }
    }

    /**
     * Wait for each WebElement from list to be visible
     *
     * @param elements WebElements list
     */
    public static void waitForElements(List<WebElement> elements) {
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " elements " + Helper.getElementLocator(elements));
            getWait().until(ExpectedConditions.visibilityOfAllElements(elements));
            for (WebElement element : elements) {
                Helper.highlight(element);
            }
            TestReport.addLog(LogStatus.INFO, "Element " + Helper.getElementLocator(elements) + " is visible");
        } catch (TimeoutException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(elements) + " is hidden or not found");
            TestReport.addLog(LogStatus.INFO, e);
            throw e;
        }
    }

    /**
     * Waits for element with text to be visible on WebElements list
     *
     * @param elements WebElements list
     * @param text     to be found on WebElement
     */
    public static void waitForElementWithTextOnList(List<WebElement> elements, String text) {
        waitForElements(elements);
        try {
            TestReport.addLog(LogStatus.INFO, Helper.getMethodName() + " elements " + Helper.getElementLocator(elements));
            getWait().until((WebDriver d) -> {
                try {
                    for (WebElement element : elements) {
                        Helper.highlight(element);
                        if (element.getText().equals(text)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
            TestReport.addLog(LogStatus.INFO, "Found element " + Helper.getElementLocator(elements) + " with text " + text);
        } catch (NoSuchElementException e) {
            TestReport.addLog(LogStatus.ERROR, "Element " + Helper.getElementLocator(elements) + "not found");
            TestReport.addLog(LogStatus.ERROR, e);
            throw e;
        }
    }
}
