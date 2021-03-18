package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team007.appalanche.R;

public class ExperimentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);



        // NO NEED TO SET UP OVERVIEW, WE'RE ON OVERVIEW PAGE

        // if TRIAL BUTTON IS CLICKED
        final Button trialButton = findViewById(R.id.trialButton);
        trialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrialActivity();
            }
        });
        // IF QUESTION BUTTON IS CLICKED
        final Button questionButton;
        questionButton = findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionActivity();
            }
        });


    }

    public void openQuestionActivity() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent,1);
    }
    public void  openTrialActivity() {
        Intent intent = new Intent(this, TrialActivity.class);
        startActivityForResult(intent,2);
    }
}