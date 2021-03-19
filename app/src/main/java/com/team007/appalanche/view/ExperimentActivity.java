package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.Trial.*;

public class ExperimentActivity extends AppCompatActivity {

    // needed a way to check for the current experiment's type so someone will def have to change this
    Experiment experiment;

    Button questionButton;
    Button addTrialButton;
    String experimentType = "binomial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);



        // NO NEED TO SET UP OVERVIEW, WE'RE ON OVERVIEW PAGE


        // IF QUESTION BUTTON IS CLICKED
        final Button questionButton;
        questionButton = findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionActivity();
            }
        });

//        addTrialButton = findViewById(R.id.addTrialButton);
//        addTrialButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openAddTrialActivity();
//            }
//        });

        // CLICK ON BACK BUTTON
        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExperimentActivity.this, MainActivity.class);
                ExperimentActivity.this.startActivity(intent);
            }
        });

        // CLICK ON TRIALS BUTTON
        final Button trialButton = findViewById(R.id.trialButton);
        trialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trialIntent = new Intent(ExperimentActivity.this, TrialActivity.class);
                ExperimentActivity.this.startActivity(trialIntent);
            }
        });

    } // end of onCreate()

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
        switch (itemID) {
            //selecting "Generate CR Code" menu item
            case R.id.generate_qr_code:
                //String expType = experiment.getTrialType();
                openQRCodeFragment(experimentType);
                return true;
            // selecting "Scan QR Code" menu item
            case R.id.scan_barcode:
                // TODO: implement scanning barcode
                return true;
            default:
                return false;
        }
    }

    public void openQuestionActivity() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent,1);
    }

    // This method starts the QR Code Fragment, passing the experiment type as a string
    // @param experimentType is a string representing the type of the current experiment
    public void openQRCodeFragment(String experimentType) {
        Intent intent = new Intent(this, QRCodeFragment.class);
        intent.putExtra("type", experimentType);
        startActivityForResult(intent, 3);
    }

    public void  openTrialActivity() {
        Intent intent = new Intent(this, TrialActivity.class);
        startActivityForResult(intent,2);
    }
}
