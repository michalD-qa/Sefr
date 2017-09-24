package pages;

import org.openqa.selenium.Keys;
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

    @FindBy(id = "gmail-create-account")
    private WebElement createAccount;

    @FindBy(className = "gmail-nav__logo gmail-logo")
    private WebElement gmailLogo;

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
        sendKeys(queryField, query + Keys.ENTER);
        waitForText(queryField, query, false);
    }

    /**
     * Opens gmail
     */
    public void openGmail() {
        click(gmail);
        waitForVisible(gmailLogo);
    }

    /**
     * Verifies that paget title contains string
     *
     * @param pageTitle string to contains on page title
     */
    public void verifyPageTitleContain(String pageTitle) {
        waitForPageTitleContain(pageTitle);
        assert (Base.getInstance().getTitle().contains(pageTitle));
    }


}
