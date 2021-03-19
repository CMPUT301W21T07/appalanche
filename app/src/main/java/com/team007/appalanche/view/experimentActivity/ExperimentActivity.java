package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.R;

import com.team007.appalanche.question.Question;
import com.team007.appalanche.view.AskQuestionFragment;
import com.team007.appalanche.view.QRCodeFragment;

import static com.team007.appalanche.view.experimentActivity.QuestionFragment.questionAdapter;
import static com.team007.appalanche.view.experimentActivity.QuestionFragment.questionList;

public class ExperimentActivity extends AppCompatActivity implements AskQuestionFragment.OnFragmentInteractionListener {
    Experiment experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment2);
        ExperimentTabAdapter sectionsPagerAdapter = new ExperimentTabAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.experiment_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.experiment_tabs);
        tabs.setupWithViewPager(viewPager);

        // Get the experiment that started the activity
        Intent intent = getIntent();
        Experiment experiment = (Experiment) intent.getSerializableExtra("Experiment");
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
                String expType = experiment.getTrialType();
                openQRCodeFragment(expType);
                return true;
            // selecting "Scan QR Code" menu item
            case R.id.scan_barcode:
                // TODO: implement scanning barcode
                return true;
            default:
                return false;
        }
    }

    // This method starts the QR Code Fragment, passing the experiment type as a string
    // @param experimentType is a string representing the type of the current experiment
    public void openQRCodeFragment(String experimentType) {
        Intent intent = new Intent(this, QRCodeFragment.class);
        intent.putExtra("type", experimentType);
        startActivity(intent);
    }

    @Override
    public void askQuestion(Question question) {
        //questionList.addQuestionToDb(question, db);
        questionList.addQuestion(question);
        questionAdapter.notifyDataSetChanged();
    }
}