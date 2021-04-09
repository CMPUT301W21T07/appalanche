package com.team007.appalanche;

import android.app.Activity;
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
        ListView searchResults = activity.expListView;
        //Experiment exp = (Experiment) searchResults.getItemAtPosition(0);
        solo.clickInList(0);

        //String resultDesc = exp.getDescription();

        // TODO: continue to implement

    }

}
