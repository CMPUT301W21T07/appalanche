package com.team007.appalanche.view.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.team007.appalanche.R;
import com.team007.appalanche.user.User;

public class ProfileActivity extends AppCompatActivity {
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Profile");

        // SET TEXT USER NAME
        TextView userName = findViewById(R.id.userName);


        // SET TEXT USER ID
        TextView userID = findViewById(R.id.userID);
        userID.setText(currentUser.getId());

    }
}