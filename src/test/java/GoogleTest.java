import org.junit.Test;
import utils.Base;
import static pages.Pages.*;

/**
 * Created by Adam on 2016-12-18.
 */
public class GoogleTest extends Base {

    @Test
    public void queryForJavaTest() {
        google.goTo();
        google.find("java");
    }

    @Test
    public void openGmailTest() {
        google.goTo();
        google.openGmail();
    }
}
