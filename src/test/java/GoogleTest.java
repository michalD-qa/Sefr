import org.junit.Test;
import utils.Base;

import static pages.Pages.*;

/**
 * Created by Adam on 2016-12-18.
 */
public class GoogleTest extends Base {

    private static final String QUERY = "java";

    @Test
    public void queryForJavaTest() {

        //Given
        google.goTo();

        //When
        google.find(QUERY);

        //Then
        google.verifyPageTitleContain(QUERY);
    }
}
