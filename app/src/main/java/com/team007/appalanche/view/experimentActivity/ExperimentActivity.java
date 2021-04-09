package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AskQuestionFragment;
import com.team007.appalanche.view.Capture;
import com.team007.appalanche.view.QRCodeActivity;
import com.team007.appalanche.view.RegisterBarcodeActivity;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddMeasurementTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddNonNegTrialFragment;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import java.util.HashMap;

import static com.team007.appalanche.view.experimentActivity.QuestionFragment.questionList;
import static com.team007.appalanche.view.experimentActivity.TrialsFragment.trialListController;

public class ExperimentActivity extends AppCompatActivity implements AskQuestionFragment.OnFragmentInteractionListener,
        AddBinomialTrialFragment.OnFragmentInteractionListener,
        AddCountTrialFragment.OnFragmentInteractionListener,
        AddMeasurementTrialFragment.OnFragmentInteractionListener,
        AddNonNegTrialFragment.OnFragmentInteractionListener,
        IgnoreAUserFragment.OnFragmentInteractionListener
{
    private Experiment experiment;
    private User currentUser;
    private ExperimentController experimentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        ExperimentTabAdapter sectionsPagerAdapter = new ExperimentTabAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.experiment_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.experiment_tabs);
        tabs.setupWithViewPager(viewPager);

        // Get the experiment that started the activity
        Intent intent = getIntent();
        experiment = (Experiment) intent.getSerializableExtra("Experiment");
        currentUser = (User) intent.getSerializableExtra("User");
        experimentController = new ExperimentController(currentUser);
    }

    // Creating the 3-dot options menu on an experiment page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (experiment.getExperimentOwnerID().equals(currentUser.getId())) {
            // If the current user is the owner, give them more options
            getMenuInflater().inflate(R.menu.owner_experiment_settings, menu);
        } else {
            // Otherwise, give them the generic options
            getMenuInflater().inflate(R.menu.general_experiment_settings, menu);
        }
        return true;
    }

    // Direct user depending on which menu item they select
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        String expType = null;
        ExperimentController experimentController = new ExperimentController(currentUser);
        switch (itemID) {
            // Selecting "Generate CR Code" menu item
            case R.id.generate_qr_code:
                if (experiment != null) expType = experiment.getTrialType();
                if (expType == null) expType = "binomial";
                openQRCodeActivity(expType);
                return true;
            // Selecting "Scan QR Code" menu item
            case R.id.register_barcode:
                scanCode();
                return true;
            // Selecting "Subscribe" menu item
            case R.id.subscribe:
                experimentController.addSubExperiment(experiment);
                return true;
                //TODO: Either remove the subscribe button or grey it out for that specific experiment, after the user subscribed to it
            // Selecting "Unpublish experiment" menu item
            case R.id.unpublish_button:
                if(currentUser.getId().matches(experiment.getExperimentOwnerID()))
                    experimentController.unpublishExp(experiment);
                else
                    Toast.makeText(ExperimentActivity.this, "Sorry, you're not the owner of this experiment, you can't unpublish this experiment", Toast.LENGTH_LONG).show();
                return true;
            case R.id.end_button:
                endExperiment();
                return true;
            default:
                return false;
        }
    }

    /** This method sets the status of the current experiment to closed (ends the experiment)
     */
    private void endExperiment() {
        // End the experiment using the experiment controller
        experimentController.endExperiment(experiment);

        // Notify the owner that the experiment was ended
        Toast.makeText(this, "The experiment has been closed.", Toast.LENGTH_LONG).show();
    }

    /** This method starts the QR Code Fragment, passing the experiment type as a string
     *
     * @param expType is a string representing the type of the current experiment
     */
    public void openQRCodeActivity(String expType) {
        Intent intent = new Intent(this, QRCodeActivity.class);
        intent.putExtra("type", expType);
        startActivityForResult(intent,3);
    }

    @Override
    public void askQuestion(Question question) {
        questionList.addQuestionToDb(question);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void scanCode() {
        // Scan the code from the zxing library
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan the desired barcode");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(Capture.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode,
                data);

        if (scanResult != null) {
            if (scanResult.getContents() != null) {
                String barcode = scanResult.getContents();
                Intent intent = new Intent(this, RegisterBarcodeActivity.class);
                intent.putExtra("Barcode", barcode);
                intent.putExtra("Experiment", experiment);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Oops, you didn't scan anything",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void addTrial(BinomialTrial trial) {
        trialListController.addBinomialTrialToDb(trial);
    }

    @Override
    public void addTrial(CountBasedTrial trial) {
        trialListController.addCountTrialToDb(trial);
    }

    @Override
    public void addTrial(MeasurementTrial trial) {
        trialListController.addMeasurementTrialToDb(trial);
    }

    @Override
    public void addTrial(NonNegativeCountTrial trial) {
        trialListController.addNonNegTrialToDb(trial);
    }

    // IGNORE A USER HERE
    @Override
    public void addIgnoredUser(User ignoredUser) {
        experiment.addIgnoredUser(ignoredUser);
        addIgnoredUserToDB(ignoredUser);
        trialListController.deleteTrials(ignoredUser);
    }

    public void addIgnoredUserToDB(User ignoredUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users/" + currentUser.getId() +"/OwnedExperiments/"+ experiment.getDescription()+"/IgnoredExperimenters");
        HashMap<String, Object> data = new HashMap<>();
        collectionReference
                .document(ignoredUser.getId())
                .set(data);
    }
}