package com.team007.appalanche;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.DialogFragment;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;
import com.team007.appalanche.view.profile.ProfileActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

public class ViewAProfileTest {
    private Solo solo;
    SharedPreferences.Editor preferencesEditor;
    SharedPreferences sharedPref;
    DialogFragment fragment;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),rule.getActivity());
        Context targetContext = getInstrumentation().getTargetContext();
        sharedPref = rule.getActivity().getPreferences(Context.MODE_PRIVATE);
        preferencesEditor = sharedPref.edit();

    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    @Test
    public void viewProfile() {
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ExperimentActivity.class);
        solo.clickOnView(solo.getView(R.id.owner));
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        assertTrue(solo.searchText(sharedPref.getString("com.team007.Appalanche.user_key", null)));
    }
}
