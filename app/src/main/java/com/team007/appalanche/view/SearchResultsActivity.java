package com.team007.appalanche.view;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;

import com.team007.appalanche.R;
import com.team007.appalanche.experiment.BinomialExperiment;
import com.team007.appalanche.experiment.CountBasedExperiment;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.experiment.NonNegativeCountExperiment;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<Experiment> expList = new ArrayList<>();
    ExperimentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toast.makeText(SearchResultsActivity.this, "hello", Toast.LENGTH_LONG).show();

        searchView = findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.search_results_list);

        // TODO: connect to firebase to retrieve a "master list" of all the published experiments, regardless of user
        // the experiments below are only to test of the ListView/adapter work

        expList.add(new BinomialExperiment("Can I do a handstand?", "region", 2, false, false, "uwu"));
        expList.add(new CountBasedExperiment("How many times have you cried " +"listening to driver's license?", "region", 2, false, true, null));


        adapter = new ExperimentAdapter(this, expList);
        listView.setAdapter(adapter);

        // TODO: allow to open experiments

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(expList.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(SearchResultsActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });*/
    }

}

