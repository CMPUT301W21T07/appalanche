package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team007.appalanche.R;

public class TrialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);


    }
}








// trial XML is not set up for these buttons yet. When ready, move into onCreate().

//        // CLICK ON OVERVIEW BUTTON
//        final Button overviewButton = findViewById(R.id.overviewButton);
//        overviewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent overviewIntent = new Intent(TrialActivity.this, ExperimentActivity.class);
//                TrialActivity.this.startActivity(overviewIntent);
//            }
//        });
//
//        // CLICK ON QUESTION BUTTON
//        final Button questionButton = findViewById(R.id.questionButton);
//        questionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent questionIntent = new Intent(TrialActivity.this, QuestionActivity.class);
//                TrialActivity.this.startActivity(questionIntent);
//            }
//        });