package com.team007.appalanche;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID; // https://stackoverflow.com/a/41762
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the UI-component of subscribing to an experiment.
 */

public class SubscriptionUITest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void mainTest(){
        String randomDesc = UUID.randomUUID().toString(); // https://stackoverflow.com/a/41762
        // Creating a mock experiment so that subscription can be tested
        User autoGenUser = new User("123", null);
        Experiment autoGenExp = new MeasurementExperiment(randomDesc, "Albania", 2, false, false, "123");
        ExperimentController experimentController = new ExperimentController(autoGenUser);
        experimentController.addExperiment(autoGenExp);

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnText(randomDesc);
        solo.assertCurrentActivity("Wrong Activity", ExperimentActivity.class);
        solo.clickOnMenuItem("Subscribe to Experiment");
        solo.clickOnActionBarHomeButton();
        solo.clickOnText("Subscribed");
        // check if subscribed item is in list
        assertTrue(solo.searchText(randomDesc));
    }
}
