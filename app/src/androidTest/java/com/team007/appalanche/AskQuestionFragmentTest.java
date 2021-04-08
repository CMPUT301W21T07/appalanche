package com.team007.appalanche;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.view.experimentActivity.QuestionFragment;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class AskQuestionFragmentTest {
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
    public void TestAskQuestion(){
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnText("aaaa");
        solo.clickOnText("Questions");
        solo.clickOnButton("ASK NEW QUESTION");
        solo.enterText((EditText) solo.getView(R.id.askQuestion),"Question");
        solo.clickOnButton("Post");
        assertTrue(solo.searchText("Question"));
    }
}
