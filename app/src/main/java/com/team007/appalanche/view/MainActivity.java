package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// import com.google.firebase.firestore.FirebaseFirestore; TODO: hook up firebase
import com.team007.appalanche.R;
import com.team007.appalanche.custom.CustomList;

import java.util.ArrayList;
import com.team007.appalanche.Experiment.Experiment;

public class MainActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    // FirebaseFirestore db; // TODO: hook up firebase


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountManager am = AccountManager.get(this); // "this" references the current Context

        Account[] accounts = am.getAccountsByType("com.google");

        // Obtain the IDs
        expList = findViewById(R.id.expList);

        ExperimentDataList = new ArrayList<>();
        Experiment newExp = new Experiment("How many jelly beans can I fit in my mouth?", "Alberta", "NonNegTrial",2, false, true, null);
        ExperimentDataList.add(newExp);
        // Set up the adapter for Experiment List View
        expAdapter = new CustomList(this, ExperimentDataList);
        expList.setAdapter(expAdapter);

        // Open the experiment
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openExperimentActivity();

            }
        });
    }

    public void openExperimentActivity() {
        Intent intent = new Intent(this, ExperimentActivity.class);
        startActivityForResult(intent,1);
    }
}