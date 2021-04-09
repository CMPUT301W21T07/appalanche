package com.team007.appalanche.view.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.ContactInfo;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {
    private User currentUser;
    FirebaseFirestore db;
    TextView userName;
    TextView phoneNumber;
    TextView gitHub;
    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Profile");

        // SET TEXT USER NAME
        userName = findViewById(R.id.userName);
        // SET TEXT USER NAME
        phoneNumber = findViewById(R.id.phoneNumber);
        gitHub = findViewById(R.id.githubLink);

//        // Fetch user info from firebase into currentUser using the given ID
        loadUserInfo();

        // SET TEXT USER ID
        TextView userID = findViewById(R.id.userID);
        userID.setText(currentUser.getId());

        // set up list view
        expList = findViewById(R.id.ownedExpList);
        ExperimentDataList = new ArrayList<Experiment>();
        // Set up the adapter for Experiment List View
        expAdapter = new CustomList(this, ExperimentDataList);
        expList.setAdapter(expAdapter);
        setUpFirebase(currentUser);

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
                String phoneNumb = (String) snapshot.getData().get("PhoneNumber");
                String github = (String) snapshot.getData().get("Github");
                ContactInfo contactInfo;
                if (phoneNumb != null)
                    contactInfo = new ContactInfo(phoneNumb, github);
                else
                    contactInfo = new  ContactInfo("", github);
                Profile profile = new Profile(name, contactInfo);
                currentUser.setProfile(profile);
                if (name != null) {
                    userName.setText(currentUser.getProfile().getUserName());
                    if (name.equals(""))
                        userName.setHint("Name");}


                if (phoneNumb != null)
                    phoneNumber.setText(String.valueOf(phoneNumb));
                else
                    phoneNumber.setText("Phone Number");


                if (github != null) {
                    gitHub.setText(github);
                    if (github.equals(""))
                        gitHub.setHint("Github Link");}
            }
        });

    }

    /*Set up the back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }

    public void setUpFirebase(User currentUser) {
        db = FirebaseFirestore.getInstance();
        //SET UP REAL TIME CHANGES FOR UI, ANYTHING IS CHANGED IN THIS COLLECTION PATH, UI ALSO CHANGES
        final CollectionReference collection = db.collection("Users/" + currentUser.getId() + "/OwnedExperiments");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list, because we're not appending here, we load the whole list over again
                ExperimentDataList.clear();
                // for every document in this collection path
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String description = doc.getId();
                    String trialType = (String) doc.getData().get("trialType");
                    Boolean expOpen = doc.getBoolean("expOpen");
                    Long minNumTrials = (Long) doc.getData().get("minNumTrials");
                    String expOwnerID = (String) doc.getData().get("expOwnerID");
                    String region = (String) doc.getData().get("region");
                    Boolean locationRequired = doc.getBoolean("locationRequired");
                    Experiment newExp = new Experiment(description, region, trialType, minNumTrials.intValue(), locationRequired, expOpen, expOwnerID);
                    // add to owned experiment in the user owned list
                    //if (experimentController.getCurrentUser().getSubscribedExperiments().contains(newExp) == false )
                    ExperimentDataList.add(newExp);
                }
                expAdapter.notifyDataSetChanged();
            }
        });
    }
}