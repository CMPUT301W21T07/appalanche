package com.team007.appalanche;

import android.app.Activity;
import android.app.Fragment;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;
import com.team007.appalanche.view.ui.mainActivity.OwnedFragment;
import com.team007.appalanche.view.ui.mainActivity.SubscribedFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the UI-component of subscribing to an experiment.
 */

public class SubscriptionUITest {
    private Solo solo;

    //TODO: figure out how to start from OwnedFragment

//    Fragment fragment = solo.getCurrentActivity().getFragmentManager().findFragmentById(SubscribedFragment.getID());

    @Rule
    public ActivityTestRule<ExperimentActivity> rule =
            new ActivityTestRule<>(ExperimentActivity.class, true, true);

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
        // Creating a mock experiment so that subscription can be tested
        User autoGenUser = new User("123", null);
        Experiment autoGenExp = new MeasurementExperiment("1", "Albania", 2, false, false, "123");
        ExperimentController experimentController = new ExperimentController(autoGenUser);
        experimentController.addExperiment(autoGenExp);
        solo.assertCurrentActivity("Wrong Fragment", OwnedFragment.class);

        solo.clickOnView(solo.getView(R.id.expList));
        solo.assertCurrentActivity("Wrong Activity", ExperimentActivity.class);
        solo.clickOnView(solo.getView(R.menu.experiment_settings));
        solo.clickOnMenuItem("Subscribe");
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.home));

        // click on 'Subscribed'
        // check if subscribed item is in list
        assertTrue(solo.searchText("testSubscription"));
    }
}
