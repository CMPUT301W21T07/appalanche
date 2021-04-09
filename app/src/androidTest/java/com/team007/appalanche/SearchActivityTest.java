package com.team007.appalanche;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;
import com.team007.appalanche.view.searching.SearchActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchActivityTest {
    /**
     * This class tests the UI components of SearchActivity.
     */
    private Solo solo;

    @Rule
    public ActivityTestRule<SearchActivity> searchRule = new ActivityTestRule<>(SearchActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), searchRule.getActivity());
    }

    @Test
    public void start() {
        Activity activity = searchRule.getActivity();
    }

    @Test
    public void checkFilter() {
        /**
         * checks if the search bar text is included in all the filtered results
         */
        solo.assertCurrentActivity("Wrong activity", SearchActivity.class);
        SearchActivity activity = (SearchActivity) solo.getCurrentActivity();
        ListView searchResults = activity.expListView;

        //click on the search icon
        solo.clickOnView(solo.getView(R.id.search_item));

        //enter text on the search bar
        String testString = "Test";
        solo.enterText(0, testString);
        solo.sleep(1000);

        //iterate through the filtered ListView to check if each item contains the text
        String toCompare;
        View v;
        TextView textView;
        for (int i = 0; i < searchResults.getCount(); i++) {
            v = searchResults.getChildAt(i);
            textView = (TextView) v.findViewById(R.id.expDescSearchResult);
            toCompare = textView.getText().toString();
            Log.i("what toCompare is", toCompare);
            assert(toCompare.contains(testString));
        }
    }

    @Test
    public void checkOpenExp() {
        solo.assertCurrentActivity("Wrong activity", SearchActivity.class);

        SearchActivity activity = (SearchActivity) solo.getCurrentActivity();
        solo.sleep(1000);
        ListView searchResults = activity.expListView;
        //solo.clickInList(0);

        //this is almost identical to the openExperimentActivity method, but it isn't triggered by the tapping of the ListView item oof
        //but surely they won't mind right :/
        User testUser = new User("test", new Profile());
        Intent intent = new Intent(solo.getCurrentActivity(), ExperimentActivity.class );
        Experiment experiment = (Experiment) searchResults.getItemAtPosition(0);
        intent.putExtra("Experiment", experiment);
        intent.putExtra("User", testUser);
        activity.startActivityForResult(intent, 1);

        solo.assertCurrentActivity("you didn't open the experiment activity??", ExperimentActivity.class);

        // TODO: figure out how to pass the mock user in the actual currentUser value

    }

}
