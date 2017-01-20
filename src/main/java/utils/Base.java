package utils;

import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * Created by Adam on 2016-12-18.
 */
public class Base {

    private static WebDriver driver;
    private static TestReport report;

    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public static void beforeClass() {
        initializeDriver();
        TestReport.setupTestReport();
    }

    @Before
    public void beforeEachTest() {
        setTestReportDetails(name.getMethodName());
    }

    @After
    public void afterEachTest() {
        report.addScreenshotToReport();
        report.setRunStatus();
        report.endTest();
    }

    @AfterClass
    public static void afterClass() {
        TestReport.endTestReport();
        closeDriver();
    }

    /**
     * Gets instance to driver
     *
     * @return instance to driver
     */

    public static WebDriver getInstance() {
        return driver;
    }

    private static void initializeDriver() {
        handlingDriver();
        driver.manage().window().maximize();
    }

    private static void closeDriver() {
        if (driver != null) {
            //close browser window
            driver.close();
            //close driver instance
            driver.quit();
        }
    }

    private void setTestReportDetails(String testName) {
        report = new TestReport(testName, true);
    }

    private static void handlingDriver() {
        String browser;
        browser = Helper.readProperties().getProperty("browser");
        System.setProperty(Constant.GECKO_DRIVER_PROPERTY, "drivers/geckodriver.exe");
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty(Constant.CHROME_DRIVER_PROPERTY, "drivers/chromedriver.exe");
                driver = new ChromeDriver();
                try {
                    Thread.sleep(500);  //to avoid chromedriver issue
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "ie":
                System.setProperty(Constant.IE_DRIVER_PROPERTY, "drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            case "phantom":
                System.setProperty(Constant.PHANTOMJS_BINARY_PROPERTY, "drivers/phantomjs-2.1.1-windows/bin/phantomjs.exe");
                driver = new PhantomJSDriver();
                break;
            default:
                driver = new FirefoxDriver();
                break;
        }
    }
}
