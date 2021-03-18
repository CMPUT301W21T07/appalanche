package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.team007.appalanche.R;

public class ExperimentActivity extends AppCompatActivity {

    Button questionButton;
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


} // end of ExperimentActivity