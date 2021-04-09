package com.team007.appalanche;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;
import com.team007.appalanche.view.profile.OwnerProfileActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EditProfileFragmentTest {
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
    public void EditProfile(){
        add.clickOnView(add.getView(R.id.app_bar_profile));
        add.assertCurrentActivity("Wrong Activity", OwnerProfileActivity.class);
        add.clickOnView(add.getView(R.id.editProfile));
        add.clearEditText((EditText) add.getView(R.id.editUserName));
        add.clearEditText((EditText) add.getView(R.id.editPhoneNumber));
        add.clearEditText((EditText) add.getView(R.id.editGithubLink));
        add.enterText((EditText) add.getView(R.id.editUserName), "username");
        add.enterText((EditText) add.getView(R.id.editPhoneNumber), "1234567890");
        add.enterText((EditText) add.getView(R.id.editGithubLink), "github.link");
        add.clickOnButton("Confirm");
        assertTrue(add.searchText("username"));
        assertTrue(add.searchText("1234567890"));
        assertTrue(add.searchText("github.link"));
    }
}
