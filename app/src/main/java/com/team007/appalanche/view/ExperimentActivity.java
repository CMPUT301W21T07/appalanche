package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team007.appalanche.R;

public class ExperimentActivity extends AppCompatActivity {

    Button questionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        questionButton = findViewById(R.id.question);

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
}