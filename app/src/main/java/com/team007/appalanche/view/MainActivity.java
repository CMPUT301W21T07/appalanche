package com.team007.appalanche.view;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.User.Profile;
import com.team007.appalanche.User.User;
import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.view.ui.main.SectionsPagerAdapter;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    private static DocumentReference currentUser;
    private static final String TAG = "Fragment Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton addExperimentButton = findViewById(R.id.addExperimentButton);
        FloatingActionButton scanCodeButton = findViewById(R.id.scanCodeButton);

        /* Create the current user in the shared preferences, if the user does not already exist */
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Check if we have a use already logged in
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);

        db = FirebaseFirestore.getInstance();

        if (userKey == null) {
            // if we don't have a stored user, then create a user account in firebase and set the
            // user key to the user document ID

            // Create a new collection in firebase and store the generated id to userKey

            currentUser = db.collection("Users").document();
            userKey = currentUser.getId();

            // Create a new instance of a user object
            Profile profile = new Profile();
            User user = new User(userKey, profile);

            // Store that user object in firebase
            currentUser.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                }
            });

            // Store the userKey in shared preferences
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("com.team007.Appalanche.user_key", userKey);
            editor.apply();
        } else {
            currentUser = db.collection("Users").document(userKey);
        }

//        Toast.makeText(MainActivity.this, userKey, Toast.LENGTH_LONG).show();

        AccountManager am = AccountManager.get(this); // "this" references the current Context
        Account[] accounts = am.getAccountsByType("com.google");

        /// TESTTTT
        final User[] us = new User[1];
        currentUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                us[0] = documentSnapshot.toObject(User.class);
                Experiment newExp = new Experiment("How many jelly beans can I fit in my mouth?",
                        "Alberta", "NonNegTrial",2, false, true, us[0].getId());
                Toast.makeText(MainActivity.this, us[0].getId(), Toast.LENGTH_LONG).show();
                us[0].addOwnedExperiment(newExp);
                currentUser.set(us[0]);
            }
        });

        addExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddExperimentFragment().show(getSupportFragmentManager(), "New ");;
            }
        });

        scanCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
    }

    private void scanCode() {
        // scan the code from the zxing library
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("For flash, use volume up key");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(Capture.class);
        integrator.initiateScan();

        // deserialize
        // get the trial from the deserialization
        // add the trial to the appropriate experiment
        // go to that experiment
    }

    @Override
    // Followed the tutorial from Coding in Flow on Youtube.com
    // Video URL: https://www.youtube.com/watch?v=oh4YOj9VkVE&ab_channel=CodinginFlow
    /**
     * This sets up a custom menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    // Followed the tutorial from Coding in Flow on Youtube.com
    // Video URL: https://www.youtube.com/watch?v=oh4YOj9VkVE&ab_channel=CodinginFlow
    /**
     * This reroutes the selected menu options to callback functions
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_profile:
                openProfileActivity();
                break;
            case R.id.app_bar_search:
                searchActivity();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                data);
        if (scanResult.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            builder.setMessage("The barcode scanned is:" + scanResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            Toast.makeText(getApplicationContext(), "Oops, you didnt scan anything",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Takes in the serialized string of a barcode and returns the barcode object
     * @param serialBarcode
     * @return Returns the deserialized barcode object
     */
    private ScannableCode deserialize(String serialBarcode) {
        Gson gson = new Gson();
        return gson.fromJson(serialBarcode, ScannableCode.class);
    }

    private void openProfileActivity() {
        //TODO: Implement
    }

    private void searchActivity() {
        //TODO: Implement
    }

}