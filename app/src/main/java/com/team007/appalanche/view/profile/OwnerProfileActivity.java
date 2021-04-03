package com.team007.appalanche.view.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AddExperimentFragment;
import com.team007.appalanche.view.EditUserInfoFragment;

public class OwnerProfileActivity extends AppCompatActivity implements EditUserInfoFragment.OnFragmentInteractionListener {
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Profile");

        // SET TEXT USER NAME
        TextView userName = findViewById(R.id.userName);


        // SET TEXT USER ID
        TextView userID = findViewById(R.id.userID);
        userID.setText(currentUser.getId());

        // Add edit user button listeners
        Button editUserButton = findViewById(R.id.editProfile);
        // String finalUserKey = userKey;
        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditUserInfoFragment().newInstance(currentUser).show(getSupportFragmentManager(), "New");
            }
        });

    }

    @Override
    public void updateUserInfo(User user) {
        //TODO: figure out what to do with this function
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
//
}