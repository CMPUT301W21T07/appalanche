package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team007.appalanche.R;
import com.team007.appalanche.Trial.*;

public class ExperimentActivity extends AppCompatActivity implements AddBinomialTrialFragment.OnFragmentInteractionListener {

    Button questionButton;
    Button addTrialButton;
    String experimentType = "binomial";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

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
    }

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

    @Override
    public void addTrial(Trial trial) {
        // TODO: hook up add trial
    }
}