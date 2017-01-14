package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.*;

import static utils.Actions.*;

/**
 * Created by Adam on 2016-12-18.
 */
public class Google {

    public Google() {
        PageFactory.initElements(Base.getInstance(), this);
    }

    @FindBy(id = "hplogo")
    private WebElement bigLogo;

    @FindBy(name = "btnI")
    private WebElement luckyFind;

    @FindBy(name = "q")
    private WebElement queryField;

    @FindBy(linkText = "Gmail")
    private WebElement gmail;

    /**
     * Opens google main page
     */
    public void goTo() {
        openPage(Constant.GOOGLE_URL);
        waitForVisible(bigLogo);
    }

    /**
     * Search for query string
     *
     * @param query
     */
    public void find(String query) {
        waitForVisible(queryField);
        sendKeys(queryField, query);
        //Actions.waitForHidden(bigLogo);
    }


    /**
     * Opens gmail
     */
    public void openGmail() {
        waitForClickable(gmail);
        click(gmail);
    }
}
