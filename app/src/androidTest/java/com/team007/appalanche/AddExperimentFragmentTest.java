package com.team007.appalanche;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddExperimentFragmentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void Setup(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void AddCountExperimentTest(){
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.addExperimentButton));
        solo.enterText((EditText) solo.getView(R.id.expDescription),"Experiment1");
        solo.enterText((EditText) solo.getView(R.id.expRegion),"Edmonton");
        solo.enterText((EditText) solo.getView(R.id.expTrials),"4");
        solo.clickOnView(solo.getView(R.id.expType));
        solo.clickOnText("COUNT");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Experiment1"));
    }

    @Test
    public void AddNonNegativeCountExperimentTest(){
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.addExperimentButton));
        solo.enterText((EditText) solo.getView(R.id.expDescription),"Experiment2");
        solo.enterText((EditText) solo.getView(R.id.expRegion),"Edmonton");
        solo.enterText((EditText) solo.getView(R.id.expTrials),"4");
        solo.clickOnView(solo.getView(R.id.expType));
        solo.clickOnText("NON NEGATIVE COUNT");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Experiment2"));
    }

    @Test
    public void AddBinomialExperimentTest() {
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

