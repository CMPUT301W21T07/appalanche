package com.team007.appalanche.view.searching;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SearchActivity extends AppCompatActivity {

    public ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ExperimentDataList = new ArrayList<Experiment>();
        setUpFirebase();

        expList = findViewById(R.id.all_experiments);
        expAdapter = new CustomList(this, ExperimentDataList);
        expList.setAdapter(expAdapter);
    }

    public void setUpFirebase(){
        db = FirebaseFirestore.getInstance();
        final CollectionReference collection = db.collection("Experiments");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                ExperimentDataList.clear(); // just a test
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
                    ExperimentDataList.add(newExp);
                }
                expAdapter.notifyDataSetChanged();
            }
        });
    }

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

                for(Experiment experiment: ExperimentDataList) {
                    if(experiment.getDescription().contains(newText))
                        results.add(experiment);
                }

                ((CustomList) expList.getAdapter()).update(results);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}