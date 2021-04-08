package com.team007.appalanche;

import android.app.Activity;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.view.searching.SearchActivity;

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

        //click on the first search icon
        solo.clickOnView(solo.getView(R.id.search_item));

        //click on the second search icon

        //enter text on the search bar

        //wait and iterate through the filtered ListView to check if each item contains the text

    }

    @Test
    public void checkOpenExp() {
        solo.assertCurrentActivity("Wrong activity", SearchActivity.class);

        SearchActivity activity = (SearchActivity) solo.getCurrentActivity();
        ListView searchResults = activity.expListView;
        Experiment exp = (Experiment) searchResults.getItemAtPosition(0);
        //activity.openExperimentActivity(exp);

        String resultDesc = exp.getDescription();

        // TODO: continue to implement

    }

}
