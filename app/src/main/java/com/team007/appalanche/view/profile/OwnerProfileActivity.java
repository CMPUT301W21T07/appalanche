package com.team007.appalanche.view.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.ContactInfo;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AddExperimentFragment;
import com.team007.appalanche.view.EditUserInfoFragment;

import java.util.HashMap;
import java.util.PrimitiveIterator;

public class OwnerProfileActivity extends AppCompatActivity implements EditUserInfoFragment.OnFragmentInteractionListener {
    private User currentUser;
    FirebaseFirestore db;
    TextView userName;
    TextView phoneNumber;
    TextView gitHub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Profile");
        // SET TEXT USER NAME
        userName = findViewById(R.id.userName);
        phoneNumber = findViewById(R.id.phoneNumber);
        gitHub = findViewById(R.id.githubLink);

//        // Fetch user info from firebase into currentUser using the given ID
        loadUserInfo();
//
        if (currentUser.getProfile() != null)
            userName.setText("name "+currentUser.getProfile().getUserName());
        else
            userName.setText("not entered");

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
        updateUserInfoOnDB(user);

    }

    /*Fetch user info from firebase  */
    public void loadUserInfo() {
        db = FirebaseFirestore.getInstance();
        //SET UP REAL TIME CHANGES FOR UI, ANYTHING IS CHANGED IN THIS DOCUMENT PATH, UI ALSO CHANGES
        final DocumentReference userDoc = db.collection("Users").document(currentUser.getId());
        userDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                String name = (String) snapshot.getData().get("Name");
                Long phoneNumb  = (Long)  snapshot.getData().get("PhoneNumber");
                String github = (String) snapshot.getData().get("Github");
                ContactInfo contactInfo = new ContactInfo(403, github);
                Profile profile = new Profile(name, contactInfo);
                currentUser.setProfile(profile);
                userName.setText(currentUser.getProfile().getUserName());
                phoneNumber.setText(String.valueOf(phoneNumb.intValue()));
                gitHub.setText(github);
            }
        });

    }

    public void updateUserInfoOnDB(User user) {
        db = FirebaseFirestore.getInstance();
        final DocumentReference userDoc = db.collection("Users").document(user.getId());
        HashMap<String, Object> data = new HashMap<>();
        data.put("Name", user.getProfile().getUserName());
        data.put("PhoneNumber", user.getProfile().getContactInfo().getPhoneNumber());
        data.put("Github", user.getProfile().getContactInfo().getGithubLink());
        userDoc.set(data);
    }
}