package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Base;
import utils.Constant;

import java.util.List;

import static utils.Actions.*;

public class Allegro {

    public Allegro() {
        PageFactory.initElements(Base.getInstance(), this);
    }

    @FindBy(id = "main-search-text")
    private WebElement mainSearch;

    @FindBy(xpath = "//h1[@class='logo']")
    private WebElement bigLogo;

    @FindBy(xpath = "//input[@value='Szukaj']")
    private WebElement searchBtn;

    @FindBy(xpath = "//div[@id='opbox-listing']//h2/a")
    private List<WebElement> resultsNameList;

    /**
     * Open allegro main page
     */
    public void goTo() {
        openPage(Constant.ALLEGRO_URL);
        waitForVisible(bigLogo);
    }

    /**
     * Search for query string
     *
     * @param query query string
     */
    public void find(String query) {
        sendKeys(mainSearch, query);
        click(searchBtn);
    }

    /**
     * Verifies that search results are visible and page title contains query string
     *
     * @param query query string
     */
    public void verifyResultsDisplayed(String query) {
        waitForPageTitleContain(query);
        waitForElements(resultsNameList);
    }
}
