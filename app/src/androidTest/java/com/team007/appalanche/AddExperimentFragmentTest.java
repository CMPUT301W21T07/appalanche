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


public class AddExperimentFragmentTest {
    private Solo solo;
    SharedPreferences.Editor preferencesEditor;
    SharedPreferences sharedPref;
    ExperimentController experimentController;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void setUp() throws Exception {
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
    public void AddCountExperimentTest(){
        preferencesEditor.putString("com.team007.Appalanche.user_key",null );
        preferencesEditor.commit();
        assertEquals(sharedPref.getString("com.team007.Appalanche.user_key", null),null);

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

        solo.clickOnView(solo.getView(R.id.addExperimentButton));
        solo.enterText((EditText) solo.getView(R.id.expDescription),"Experiment2");
        solo.enterText((EditText) solo.getView(R.id.expRegion),"Edmonton");
        solo.enterText((EditText) solo.getView(R.id.expTrials),"4");
        solo.clickOnView(solo.getView(R.id.expType));
        solo.clickOnText("COUNT");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Experiment2"));
    }

    @Test
    public void AddNonNegativeCountExperimentTest(){
        preferencesEditor.putString("com.team007.Appalanche.user_key",null );
        preferencesEditor.commit();
        assertEquals(sharedPref.getString("com.team007.Appalanche.user_key", null),null);
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.addExperimentButton));
        solo.enterText((EditText) solo.getView(R.id.expDescription),"Experiment21");
        solo.enterText((EditText) solo.getView(R.id.expRegion),"Edmonton");
        solo.enterText((EditText) solo.getView(R.id.expTrials),"4");
        solo.clickOnView(solo.getView(R.id.expType));
        solo.clickOnText("NON NEGATIVE COUNT");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Experiment21"));
    }

    @Test
    public void AddBinomialExperimentTest() {
        preferencesEditor.putString("com.team007.Appalanche.user_key",null );
        preferencesEditor.commit();
        assertEquals(sharedPref.getString("com.team007.Appalanche.user_key", null),null );
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.addExperimentButton));
        solo.enterText((EditText) solo.getView(R.id.expDescription),"Experiment3");
        solo.enterText((EditText) solo.getView(R.id.expRegion),"Edmonton");
        solo.enterText((EditText) solo.getView(R.id.expTrials),"4");
        solo.clickOnView(solo.getView(R.id.expType));
        solo.clickOnText("BINOMIAL");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Experiment3"));
    }

    @Test
    public void AddMeasurementExperimentTest(){
        preferencesEditor.putString("com.team007.Appalanche.user_key",null );
        preferencesEditor.commit();
        assertEquals(sharedPref.getString("com.team007.Appalanche.user_key", null),null);
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.addExperimentButton));
        solo.enterText((EditText) solo.getView(R.id.expDescription),"Experiment4");
        solo.enterText((EditText) solo.getView(R.id.expRegion),"Edmonton");
        solo.enterText((EditText) solo.getView(R.id.expTrials),"4");
        solo.clickOnView(solo.getView(R.id.expType));
        solo.clickOnText("MEASUREMENT");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Experiment4"));
    }
}

