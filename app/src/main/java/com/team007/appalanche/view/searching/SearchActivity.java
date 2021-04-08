package com.team007.appalanche.view.searching;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * This activity is opened when a user wants to search for an
 * experiment they can subscribe to. In this activity,
 * a user can access all published experiments from other
 * users, and can filter experiments using a search function;
 * clicking on an experiment will open it and
 * the user can subscribe to it if desired.
 */

public class SearchActivity extends AppCompatActivity {

    public ListView expListView;
    CustomList expAdapter;
    ArrayList<Experiment> expDataList;
    ArrayList<Experiment> allExperiments;
    FirebaseFirestore db;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        expDataList = new ArrayList<Experiment>();
        allExperiments = new ArrayList<Experiment>();
        setUpFirebase();

        expListView = findViewById(R.id.all_experiments);
        expAdapter = new CustomList(this, expDataList);
        expListView.setAdapter(expAdapter);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("User");

        // Open the experiment
        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Experiment experiment = expDataList.get(position);
                openExperimentActivity(experiment);
            }
        });
    }

    /**
     * This opens the experiment activity.
     * @param experiment This is the experiment activity to open.
     */
    private void openExperimentActivity(Experiment experiment) {
        Intent intent = new Intent(this, ExperimentActivity.class);
        intent.putExtra("Experiment", experiment);
        intent.putExtra("User", currentUser);
        startActivityForResult(intent,1);
    }

    public void setUpFirebase(){
        db = FirebaseFirestore.getInstance();
        final CollectionReference collection = db.collection("Experiments");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                expDataList.clear();
                allExperiments.clear();
                // for every document in this collection path
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String description = doc.getId();
                    String trialType = (String) doc.getData().get("trialType");
                    Boolean expOpen =  doc.getBoolean("expOpen");
                    Long minNumTrials  = (Long)    doc.getData().get("minNumTrials");
                    String expOwnerID = (String) doc.getData().get("expOwnerID");
                    String region = (String) doc.getData().get("region");
                    Boolean locationRequired =  doc.getBoolean("locationRequired");
                    Experiment newExp = new Experiment(description, region, trialType, minNumTrials.intValue(), locationRequired, expOpen, expOwnerID );
                    expDataList.add(newExp);
                    allExperiments.add(newExp);
                }
                expAdapter.notifyDataSetChanged();
            }
        });
    }

    // https://www.youtube.com/watch?v=7Sw98YZW-ik
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // inflates search_bar.xml and display it in Appalanche
        getMenuInflater().inflate(R.menu.search_bar,menu);

        MenuItem menuItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Experiment> results = new ArrayList<>();

                for(Experiment experiment: allExperiments) {
                    if(experiment.getDescription().contains(newText))
                        results.add(experiment);
                }

                expDataList.clear();
                expDataList.addAll(results);
                expAdapter.notifyDataSetChanged();

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
        
        
    }
}