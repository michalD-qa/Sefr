package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adam on 2017-01-04.
 */
public class Screenshot {

    protected String captureScreenShot() {
        // Take screenshot and store as a file format
        File src = ((TakesScreenshot) Base.getInstance()).getScreenshotAs(OutputType.FILE);
        String screenshot = Helper.readProperties().getProperty("screenshot") + "Screenshot_" + Helper.getCurrentDate() + ".jpg";
        try {
            FileUtils.copyFile(src, new File(screenshot));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshot;
    }
}