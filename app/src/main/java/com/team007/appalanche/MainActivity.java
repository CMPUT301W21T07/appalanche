package com.team007.appalanche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the IDs
        expList = findViewById(R.id.expList);


        ExperimentDataList = new ArrayList<>();
        Experiment newExp = new Experiment("How many jelly beans can I fit into my mouth?");
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