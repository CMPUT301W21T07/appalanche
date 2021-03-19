package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.R;
import com.team007.appalanche.Trial.CountBasedTrial;
import com.team007.appalanche.Trial.Trial;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.question.Question;

import java.util.ArrayList;
import java.util.List;

public class TrialActivity extends AppCompatActivity implements AddBinomialTrialFragment.OnFragmentInteractionListener {

    private ListView trialListView;
    private ArrayAdapter<Trial> trialAdapter;
    private ArrayList<Trial> trialDataList;
    private String experimentType = "binomial"; //TODO: hook this up to the actual experiment type, not hard coded value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);


        // CLICK ON BACK BUTTON
        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainActivity();
            }
        });

        //NO NEED TO SET UP FOR OPTION BUTTON, WE'RE AT THE TRIAL PAGE

        // CLICK ON OVERVIEW BUTTON
        final Button overviewButton = findViewById(R.id.overviewButton);
        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOverviewActivity();
            }
        });

        // NO NEED FOR TRIAL BUTTON

        // CLICK ON QUESTION BUTTON
        final Button questionButton = findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionActivity();
            }
        });

        //CLICK ON ADD TRIAL BUTTON -> GO TO FRAGMENT
        final Button addTrialButton = findViewById(R.id.addTrialButton);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTrialActivity();
            }
        });

//         SET UP LISTVIEW
//         A LIST VIEW OF QUESTION LIST
//         MIGHT USE A CONTROLLER HERE TO ADD ITEM INTO THE LIST
//        trialDataList = new ArrayList<Trial>();
//
//        // set adapter to the new TrialCustomList
//        trialAdapter = new TrialCustomList(this, trialDataList);
//        // Obtain id from layout for Trial List View
//        trialListView = findViewById(R.id.trialList);
//        // Set the content for TrialListView
//        trialListView.setAdapter(trialAdapter);
//

    }


    public void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent,1);
    }
    public void openQuestionActivity() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent,1);
    }
    public void openOverviewActivity() {
        Intent intent = new Intent(this, ExperimentActivity.class);
        startActivityForResult(intent,1);
    }
    public void openAddTrialActivity() {
        switch(experimentType) {
            case "binomial":
                new AddBinomialTrialFragment().show(getSupportFragmentManager(), "Add_Trial");
                break;
            case "count":
                new AddCountTrialFragment().show(getSupportFragmentManager(), "Add_Trial");
                break;
            case "Measurement":
                new AddMeasurementTrialFragment().show(getSupportFragmentManager(), "Add_trial");
                break;
            case "nonNegative":
                new AddNonNegTrialFragment().show(getSupportFragmentManager(), "Add_Trial");
                break;
        }
        // TODO: hook fragment result to update experiment and create trial
    }

    @Override
    public void addTrial(Trial trial) {
        // TODO: implement and write result to firestore
    }
}




