package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                // TODO: implement generating QR Code
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
}