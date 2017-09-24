import org.junit.Test;
import utils.Base;

import static pages.Pages.*;

public class AllegroTests extends Base {

    private static final String QUERY = "Rower";

    @Test
    public void queryForBikeTest() {

        //Given
        allegro.goTo();

        //When
        allegro.find(QUERY);

        //Then
        allegro.verifyResultsDisplayed(QUERY);
    }
}