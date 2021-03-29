package com.team007.appalanche.view.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;
import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.view.AddExperimentFragment;
import com.team007.appalanche.view.Capture;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;

import java.util.HashMap;

import static com.team007.appalanche.view.ui.mainActivity.SubscribedFragment.experimentController;

public class MainActivity extends AppCompatActivity  implements AddExperimentFragment.OnFragmentInteractionListener{
    FirebaseFirestore db;
    public static User currentUser;
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

        /* Create the current user in the shared preferences, if the user does not already exist */
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Check if we have a use already logged in
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef;
        if (userKey == null) {

            // if we don't have a stored user, then create a user account in firebase and set the
            // user key to the user document ID

            // Create a new collection in firebase and store the generated id to userKey
            docRef = db.collection("Users").document();
            userKey = docRef.getId();

            // Create a new instance of a user object
            Profile profile = new Profile();
            currentUser = new User(userKey, profile);

            // Store that user object in firebase\//
            // docRef.set(currentUser)
            HashMap<String, Object> data = new HashMap<>();
            data.put("test", currentUser);
            docRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
        } else {
            docRef = db.collection("Users").document(userKey);
            HashMap<String, Object> data = new HashMap<>();
            data.put("test", "123");
            docRef.set(data);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    currentUser = documentSnapshot.toObject(User.class);
                }
            });
            //Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();

        }

//                final User[] us = new User[1];
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                us[0] = documentSnapshot.toObject(User.class);
//                Experiment newExp = new Experiment("How many jelly beans can I fit in my mouth?",
//                        "Alberta", "NonNegTrial",2, false, true, us[0].getId());
////                Toast.makeText(MainActivity.this, us[0].getId(), Toast.LENGTH_LONG).show();
//                us[0].addOwnedExperiment(newExp);
//                docRef.set(us[0]);
//            }
//        });

        // Add floating action button click listeners
        FloatingActionButton addExperimentButton = findViewById(R.id.addExperimentButton);
        String finalUserKey = userKey;
        addExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddExperimentFragment().show(getSupportFragmentManager(), "New ");
            }
        });

        FloatingActionButton scanCodeButton = findViewById(R.id.scanCodeButton);
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

    /**
     * This is overwritten here to obtain the result of the QR/Barcode scanning
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                data);
        if (scanResult.getContents() != null) {
            if (scanResult.getFormatName() == BarcodeFormat.QR_CODE.toString()) {
                // We have scanned a QR code
                getScannedTrialQR(scanResult.getContents());
            } else {
                // We have scanned a barcode
                getScannedTrialBarcode(scanResult.getContents());
            }
        } else {
            Toast.makeText(getApplicationContext(), "Oops, you didn't scan anything",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This takes in a scanned QR code, deserializes it, adds a trial to the corresponding
     * experiment class, and navigates to it.
     * @param contents This is the serialized QR code class.
     */
    private void getScannedTrialQR(String contents) {
        // deserialize
        ScannableCode scannedCode = deserialize(contents);

        // get the trial from the deserialization
        Trial trial = scannedCode.scan(currentUser);

        // add the trial to the appropriate experiment
        Experiment experiment = scannedCode.getExperiment();
        experiment.addTrial(trial);

        // go to that experiment
        openExperimentActivity(experiment);
    }

    /**
     * This takes in a scanned barcode, queries the database for it, and based on the result it
     * will either resolve to no trial or add a trial to the corresponding experiment class and
     * navigate to it.
     * @param barcode This is the scanned barcode
     */
    private void getScannedTrialBarcode(String barcode) {
        db.collection("Barcodes").document(barcode).get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            ScannableCode scannedCode = document.toObject(ScannableCode.class);
                            // get the trial from the deserialization
                            Trial trial = scannedCode.scan(currentUser);

                            // add the trial to the appropriate experiment
                            Experiment experiment = scannedCode.getExperiment();
                            experiment.addTrial(trial);

                            // go to that experiment
                            openExperimentActivity(experiment);
                        } else {
                            Toast.makeText(MainActivity.this, "This barcode is not registered to " +
                                            "anything",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "There was an error fetching the result" +
                                        " of this barcode",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
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

    /**
     * This opens the experiment activity.
     * @param experiment This is the experiment activity to open.
     */
    private void openExperimentActivity(Experiment experiment) {
        Intent intent = new Intent(this, ExperimentActivity.class);
        intent.putExtra("Experiment", experiment);
        startActivityForResult(intent,1);
    }

    private void openProfileActivity() {
        //TODO: Implement
    }

    private void searchActivity() {
        //TODO: Implement
    }


    @Override
    public void addExperiment(Experiment newExp) {
        experimentController.addExperiment(newExp);
    }
    //public void subscribedExp
}