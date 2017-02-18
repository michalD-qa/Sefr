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
        TestReport.addScreenshotToReport();
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
        String browser = Helper.readProperties().getProperty("browser");
        String system = Helper.readProperties().getProperty("system");
        if (system.equals("windows")) {
            System.setProperty(Constant.GECKO_DRIVER_PROPERTY, "drivers/windows/geckodriver.exe");
            System.setProperty(Constant.CHROME_DRIVER_PROPERTY, "drivers/windows/chromedriver.exe");
            System.setProperty(Constant.IE_DRIVER_PROPERTY, "drivers/windows/IEDriverServer.exe");
            System.setProperty(Constant.PHANTOMJS_BINARY_PROPERTY, "drivers/windows/phantomjs-2.1.1-windows/bin/phantomjs.exe");
        } else if (system.equals("linux")) {
            System.setProperty(Constant.GECKO_DRIVER_PROPERTY, "drivers/linux/geckodriver");
            System.setProperty(Constant.CHROME_DRIVER_PROPERTY, "drivers/linux/chromedriver");
            System.setProperty(Constant.PHANTOMJS_BINARY_PROPERTY, "drivers/linux/phantomjs-2.1.1-linux-x86_64/bin/phantomjs");
        }

        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                try {
                    Thread.sleep(500);  //to avoid chromedriver issue
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "phantom":
                driver = new PhantomJSDriver();
                break;
        }
    }
}
