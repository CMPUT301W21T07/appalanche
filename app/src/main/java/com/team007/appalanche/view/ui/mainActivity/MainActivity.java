package com.team007.appalanche.view.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.controller.TrialListController;
import com.team007.appalanche.experiment.BinomialExperiment;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.scannableCode.BinomialScannableCode;
import com.team007.appalanche.scannableCode.CountBasedScannableCode;
import com.team007.appalanche.scannableCode.MeasurementScannableCode;
import com.team007.appalanche.scannableCode.NonNegScannableCode;
import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AddExperimentFragment;
import com.team007.appalanche.view.AddUserIDFragment;
import com.team007.appalanche.view.Capture;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddMeasurementTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddNonNegTrialFragment;
import com.team007.appalanche.view.profile.OwnerProfileActivity;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;
import com.team007.appalanche.view.searching.SearchActivity;

import java.util.HashMap;

import static com.team007.appalanche.view.ui.mainActivity.SubscribedFragment.experimentController;

public class MainActivity extends AppCompatActivity implements AddExperimentFragment.OnFragmentInteractionListener, AddUserIDFragment.OnFragmentInteractionListener {
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

        // Create the current user in the shared preferences, if the user does not already exist
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Check if we have a user already logged in
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);

        db = FirebaseFirestore.getInstance();
        if (userKey == null) {
            // Get the text from the fragment and get the new user ID
            DialogFragment dialog = new AddUserIDFragment();
            dialog.show(getSupportFragmentManager(), "New UserID Fragment");
        } else {
            getExistingUser(userKey);
        }

        // Add floating action button click listeners
        FloatingActionButton addExperimentButton = findViewById(R.id.addExperimentButton);

        addExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddExperimentFragment().newInstance(currentUser).show(getSupportFragmentManager(), "New");
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
        // Scan the code from the zxing library
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan the barcode or QR code");
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
                openProfileActivity(currentUser);
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
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                data);

        // If the activity is a scan activity
        if (scanResult != null) {
            if (scanResult.getContents() != null) {
                if (scanResult.getFormatName().equals(BarcodeFormat.QR_CODE.toString())) {
                   // We have scanned a QR code
                   getScannedTrialQR(scanResult.getContents());
                } else {
                   // We have scanned a barcode
                   getScannedTrialBarcode(scanResult.getContents());
                }
            } else Toast.makeText(getApplicationContext(), "Oops, you did not scan anything",
                    Toast.LENGTH_SHORT).show();
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * This takes in a scanned QR code, deserializes it, adds a trial to the corresponding
     * experiment class, and navigates to it.
     * @param contents This is the serialized QR code class.
     */
    private void getScannedTrialQR(String contents) {
        // Deserialize
        ScannableCode scannedCode = deserialize(contents);

        // Get experiment information
        Experiment experiment = scannedCode.getExperiment();
        TrialListController trialListController = new TrialListController(experiment);

        switch(experiment.getTrialType()) {
            case "binomial":
                BinomialTrial binomialTrial =
                        ((BinomialScannableCode) scannedCode).scan(currentUser);
                trialListController.addBinomialTrialToDb(binomialTrial);
                break;
            case "count":
                CountBasedTrial countBasedTrial =
                        ((CountBasedScannableCode) scannedCode).scan(currentUser);
                trialListController.addCountTrialToDb(countBasedTrial);
                break;
            case "measurement":
                MeasurementTrial measurementTrial =
                        ((MeasurementScannableCode) scannedCode).scan(currentUser);
                trialListController.addMeasurementTrialToDb(measurementTrial);
                break;
            case "nonNegativeCount":
                NonNegativeCountTrial nonNegativeCountTrial =
                        ((NonNegScannableCode) scannedCode).scan(currentUser);
                trialListController.addNonNegTrialToDb(nonNegativeCountTrial);
            default:
                System.out.println("There was an error adding the trial to " +
                        "the database");
                break;
        }

        // Go to that experiment
        Toast.makeText(this, "Your trial has been " +
                        "successfully added to the experiment: " + experiment.getDescription(),
                Toast.LENGTH_LONG).show();
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

                            // Add the trial to the appropriate experiment
                            Experiment experiment = scannedCode.getExperiment();
                            TrialListController trialListController = new TrialListController(experiment);

                            switch(experiment.getTrialType()) {
                                case "binomial":
                                    BinomialScannableCode binomialScannableCode =
                                            document.toObject(BinomialScannableCode.class);
                                    BinomialTrial binomialTrial =
                                            (binomialScannableCode).scan(currentUser);
                                    trialListController.addBinomialTrialToDb(binomialTrial);
                                    break;
                                case "count":
                                    CountBasedScannableCode countBasedScannableCode =
                                            document.toObject(CountBasedScannableCode.class);
                                    CountBasedTrial countBasedTrial =
                                            (countBasedScannableCode).scan(currentUser);
                                    trialListController.addCountTrialToDb(countBasedTrial);
                                    break;
                                case "measurement":
                                    MeasurementScannableCode measurementScannableCode =
                                            document.toObject(MeasurementScannableCode.class);
                                    MeasurementTrial measurementTrial =
                                            (measurementScannableCode).scan(currentUser);
                                    trialListController.addMeasurementTrialToDb(measurementTrial);
                                    break;
                                case "nonNegativeCount":
                                    NonNegScannableCode nonNegScannableCode =
                                            document.toObject(NonNegScannableCode.class);
                                    NonNegativeCountTrial nonNegativeCountTrial =
                                            (nonNegScannableCode).scan(currentUser);
                                    trialListController.addNonNegTrialToDb(nonNegativeCountTrial);
                                default:
                                    System.out.println("There was an error adding the trial to " +
                                            "the database");
                                    break;
                            }

                            Toast.makeText(getApplicationContext(), "Your trial has been " +
                                            "successfully added to the experiment: " + experiment.getDescription(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "This barcode is not registered to " +
                                            "anything",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "There was an error fetching the result" +
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
        intent.putExtra("User", currentUser);
        startActivityForResult(intent,1);
    }

    private void openProfileActivity(User user) {
        Intent intent = new Intent(this, OwnerProfileActivity.class);
        intent.putExtra("Profile", user);
        startActivityForResult(intent,1);
    }

    private void searchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("User", currentUser);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void addExperiment(Experiment newExp) {
        experimentController.addExperiment(newExp);
        experimentController.addSubExperiment(newExp);
    }

    @Override
    public void addUserID(User newUser) {
        currentUser = newUser;
        newUserSetup();
    }

    // Set up new user on firebase and shared preference
    public void newUserSetup() {
        // Set up firebase for new user
        DocumentReference docRef = db.collection("Users").document(currentUser.getId());
        HashMap<String, Object> data = new HashMap<>();
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

        // Add value into sharePreference file so that user will be recognized next time using
        // the app
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("com.team007.Appalanche.user_key", currentUser.getId());
        editor.apply();
    }

    // Get existing user info on firebase
    public void getExistingUser(String userKey) {
        DocumentReference docRef;
        docRef = db.collection("Users").document(userKey);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = new User(documentSnapshot.getId());
            }
        });
    }
}