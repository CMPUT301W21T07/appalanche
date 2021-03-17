//Source: Lab 7 [Intent Testing]

package com.team007.appalanche;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.model.Question;
import com.team007.appalanche.view.ReplyActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

public class ReplyActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ReplyActivity> rule =
            new ActivityTestRule<>(ReplyActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkList(){
        solo.assertCurrentActivity("Wrong Activity", ReplyActivity.class);

        solo.enterText((EditText)solo.getView(R.id.reply_message), "Great idea!");
        solo.clickOnView(solo.getView(R.id.reply_button));
        solo.clearEditText((EditText)solo.getView(R.id.reply_message));

        ReplyActivity activity = (ReplyActivity) solo.getCurrentActivity();
//        final ListView replyList = activity.?;

        // check that first list item is "Great idea!"



    }

}
