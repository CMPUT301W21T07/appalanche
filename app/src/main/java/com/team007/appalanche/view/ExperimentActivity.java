package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.team007.appalanche.R;
import com.team007.appalanche.Trial.*;

public class ExperimentActivity extends AppCompatActivity {

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

    public void openQuestionActivity() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent,1);
    }


//    public void openAddTrialActivity() {
//        switch(experimentType) {
//            case "binomial":
//                new AddBinomialTrialFragment().show(getSupportFragmentManager(), "Add_Trial");
//                break;
//            case "count":
//                new AddCountTrialFragment().show(getSupportFragmentManager(), "Add_Trial");
//                break;
//            case "Measurement":
//                new AddMeasurementTrialFragment().show(getSupportFragmentManager(), "Add_trial");
//                break;
//            case "nonNegative":
//                new AddNonNegTrialFragment().show(getSupportFragmentManager(), "Add_Trial");
//                break;
//        }
//    }

    public void  openTrialActivity() {
        Intent intent = new Intent(this, TrialActivity.class);
        startActivityForResult(intent,2);
    }
}

