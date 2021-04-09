package com.team007.appalanche;
import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;
import com.robotium.solo.Solo;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MapTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void Setup(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }
    @Test
    public void TestGeomap() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnText("Measurement1");
        solo.clickOnText("Trials");
        solo.clickOnButton("VIEW MAP");
        solo.sleep(4000);
        solo.clickOnActionBarHomeButton();
        solo.clickOnText("Overview");
        assertTrue(solo.searchText("Measurement1"));

    }
}
