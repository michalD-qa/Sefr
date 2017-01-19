package utils;

import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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
        TestReport.setupTestReport(false);
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
        //closeDriver();
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
        //close browser window
        driver.close();
        driver.quit();
    }

    private void setTestReportDetails(String testName) {
        report = new TestReport(testName);
    }

    private static void handlingDriver() {
        String browser;
        browser = Helper.readProperties().getProperty("browser");
        System.setProperty("webdriver.gecko.driver","drivers/geckodriver.exe");
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver","drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            case "phantom":
                System.setProperty("phantomjs.binary.path","drivers/phantomjs-2.1.1-windows/bin/phantomjs.exe");
                driver = new PhantomJSDriver();
                break;
            default:
                driver = new FirefoxDriver();
                break;
        }
    }
}
