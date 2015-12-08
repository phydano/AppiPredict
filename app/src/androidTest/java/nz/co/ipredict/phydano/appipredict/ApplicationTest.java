package nz.co.ipredict.phydano.appipredict;

import android.app.Application;
import android.test.ActivityUnitTestCase;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

}

class BrowsePredictionTest extends ActivityUnitTestCase<BrowsePrediction> {
    public BrowsePredictionTest() { super(BrowsePrediction.class);}


}