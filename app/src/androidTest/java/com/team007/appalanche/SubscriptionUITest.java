package com.team007.appalanche;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

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

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the UI-component of subscribing to an experiment.
 */

public class SubscriptionUITest {
    private Solo solo;
    SharedPreferences.Editor preferencesEditor;
    SharedPreferences sharedPref;
    ExperimentController experimentController;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        Context targetContext = getInstrumentation().getTargetContext();
        sharedPref = rule.getActivity().getPreferences(Context.MODE_PRIVATE);
        preferencesEditor = sharedPref.edit();
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void mainTest(){
        // current user
        preferencesEditor.putString("com.team007.Appalanche.user_key", "@yoloswag");
        preferencesEditor.commit();
        assertEquals(sharedPref.getString("com.team007.Appalanche.user_key", null),"@yoloswag" );

        solo.clickOnView(solo.getView(R.id.app_bar_search)); // get ID of MainActivity search icon
        solo.clickOnText("Measurement3");

        solo.assertCurrentActivity("Wrong Activity", ExperimentActivity.class);
        solo.clickOnMenuItem("Subscribe to Experiment");
        solo.clickOnActionBarHomeButton();
        solo.clickOnText("Subscribed");
        // check if subscribed item is in list
        assertTrue(solo.searchText("Measurement3"));
    }
}
