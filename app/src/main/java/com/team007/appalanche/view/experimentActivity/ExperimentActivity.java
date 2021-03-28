package com.team007.appalanche.view.experimentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.R;

import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AskQuestionFragment;
import com.team007.appalanche.view.Capture;
import com.team007.appalanche.view.QRCodeActivity;
import com.team007.appalanche.view.RegisterBarcodeActivity;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;

import static com.team007.appalanche.view.experimentActivity.QuestionFragment.questionAdapter;
import static com.team007.appalanche.view.experimentActivity.QuestionFragment.questionList;
import static com.team007.appalanche.view.experimentActivity.TrialsFragment.trialListController;
//import static com.team007.appalanche.view.ui.mainActivity.SubscribedFragment.experimentController;

public class ExperimentActivity extends AppCompatActivity implements AskQuestionFragment.OnFragmentInteractionListener, AddBinomialTrialFragment.OnFragmentInteractionListener, AddCountTrialFragment.OnFragmentInteractionListener {
    Experiment experiment;

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
    }

    //When the 3-dot options menu is selected on an experiment page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.experiment_settings, menu);
        return true;
    }
    // Direct user depending on which menu item they select
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        String expType = null;
        switch (itemID) {
            //selecting "Generate CR Code" menu item
            case R.id.generate_qr_code:
                if (experiment != null) expType = experiment.getTrialType();
                if (expType == null) expType = "binomial";
                openQRCodeActivity(expType);
                return true;
            // selecting "Scan QR Code" menu item
            case R.id.register_barcode:
                scanCode();
                return true;
            case R.id.subscribe:
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
                User currentUser = new User(userKey);
                ExperimentController experimentController = new ExperimentController(currentUser);
                experimentController.addSubExperiment(experiment);
                //TODO: Either remove the subscribe button or grey it out for that specific experiment, after the user subscribed to it
            case R.id.close_button:
                //TODO: implement
            case R.id.end_button:
                //TODO: implement
            default:
                return false;
        }
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

    /** This method starts the QR Code Fragment, passing the experiment type as a string
     *
     * @param expType is a string representing the type of the current experiment
     */
    public void openBarcodeActivity(String expType) {
        Intent intent = new Intent(this, RegisterBarcodeActivity.class);
        //intent.putExtra("type", expType);
        startActivityForResult(intent,3);
    }

    @Override
    public void askQuestion(Question question) {
        //questionList.addQuestionToDb(question, db);
        questionList.addQuestionToDb(question);
        //questionAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void scanCode() {
        // scan the code from the zxing library
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

    @Override
    public void addTrial(BinomialTrial trial) {
        trialListController.addTrialToDb(trial);
    }

    @Override
    public void addTrial(CountBasedTrial trial) {
        trialListController.addTrialToDb(trial);
    }
}