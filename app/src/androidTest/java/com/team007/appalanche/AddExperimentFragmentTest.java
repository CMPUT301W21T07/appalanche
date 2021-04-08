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
    private Solo add;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);
    @Before
    public void Setup(){
        add = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void checklist(){
        add.assertCurrentActivity("Wrong Activity",MainActivity.class);
        add.clickOnView(add.getView(R.id.addExperimentButton));
        add.enterText((EditText) add.getView(R.id.expDescription),"Experiment833712");
        add.enterText((EditText) add.getView(R.id.expRegion),"Edmonton");
        add.enterText((EditText) add.getView(R.id.expTrials),"4");
        add.clickOnView(add.getView(R.id.expType));
        add.clickOnText("COUNT");
        add.clickOnButton("Post");
        assertTrue(add.searchText("Experiment833712"));
    }
    /*@Test
    public void checklist1(){
        add.assertCurrentActivity("Wrong Activity",MainActivity.class);
        add.clickOnView(add.getView(R.id.addExperimentButton));
        add.enterText((EditText) add.getView(R.id.textView2),"Experiment833707");
        add.enterText((EditText) add.getView(R.id.textView3),"Edmonton");
        add.enterText((EditText) add.getView(R.id.textView4),"4");
        add.clickOnButton("Non Negative Count");
        add.clickOnButton("Post");
        assertTrue(add.searchText("Edmonton"));

    }
    @Test
    public void checklist2(){
        add.assertCurrentActivity("Wrong Activity",MainActivity.class);
        add.clickOnView(add.getView(R.id.addExperimentButton));
        add.enterText((EditText) add.getView(R.id.textView2),"Experiment833711");
        add.enterText((EditText) add.getView(R.id.textView3),"Edmonton");
        add.enterText((EditText) add.getView(R.id.textView4),"4");
        add.clickOnButton("binomial");
        add.clickOnButton("Post");
        assertTrue(add.searchText("Edmonton"));
    }
    @Test
    public void checklist3(){
        add.assertCurrentActivity("Wrong Activity",MainActivity.class);
        add.clickOnView(add.getView(R.id.addExperimentButton));
        add.enterText((EditText) add.getView(R.id.textView2),"Experiment8337");
        add.enterText((EditText) add.getView(R.id.textView3),"Edmonton");
        add.enterText((EditText) add.getView(R.id.textView4),"4");
        add.clickOnButton("measurement");
        add.clickOnButton("Post");
        assertTrue(add.searchText("Edmonton"));
    }*/

}
