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
    private Solo ask;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void Setup(){
        ask = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }
    @Test
    public void TestAskQuestion(){
        ask.assertCurrentActivity("Wrong Activity",MainActivity.class);
        ask.clickOnText("aaaa");
        ask.clickOnText("Questions");
        ask.clickOnButton("ASK NEW QUESTION");
        ask.enterText((EditText) ask.getView(R.id.askQuestion),"Question");
        ask.clickOnButton("Post");
        assertTrue(ask.searchText("Question"));
    }
}
