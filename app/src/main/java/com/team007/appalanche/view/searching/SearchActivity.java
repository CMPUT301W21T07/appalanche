package com.team007.appalanche.view.searching;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.Location;
import com.team007.appalanche.R;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                getIgnoredList(experiment, new SetIgnoredList(){
                    @Override
                    public void setIgnore(Experiment experiment) {
                        getTrialList(experiment, new SetTrialList() {
                            @Override
                            public void setTrial(Experiment experiment) {
                                openExperimentActivity(experiment);
                            }
                        });
                    }
                });
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
                    Long minNumTrials  = (Long) doc.getData().get("minNumTrials");
                    String expOwnerID = (String) doc.getData().get("expOwnerID");
                    String region = (String) doc.getData().get("region");
                    int minNumValue = minNumTrials != null ? minNumTrials.intValue() : 0;
                    Boolean locationRequired =  doc.getBoolean("locationRequired");
                    Experiment newExp = new Experiment(description, region, trialType, minNumValue,
                            locationRequired, expOpen, expOwnerID );
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

    interface SetTrialList {
        void setTrial(Experiment experiment);
    }
    public void getTrialList(Experiment experiment, SetTrialList trialList) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> col =  db.collection("Experiments/"+experiment.getDescription()+"/Trials").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete (@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot doc : documents) {
                        if (experiment.getTrialType().equals("count")) {
                            Log.d(TAG, String.valueOf(doc.getData().get("description")));
                            String id = (String) doc.getData().get("userAddedTrial");
                            Date date = (Date) doc.getTimestamp("date").toDate();
                            User addedUser = new User(id);

                            if (experiment.getLocationRequired()) {
                                Double longitude = (Double) doc.getData().get("longitude");
                                Double latitude = (Double) doc.getData().get("latitude");
                                Location location = new Location(latitude, longitude);

                                experiment.addTrial( new CountBasedTrial(addedUser, location, date));
                            } else {
                                experiment.addTrial( new CountBasedTrial(addedUser, date));
                            }
                        }
                        else if (experiment.getTrialType().equals("binomial")){
                            Log.d(TAG, String.valueOf(doc.getData().get("description")));
                            Boolean success= (Boolean) doc.getData().get("binomial");
                            String id = (String) doc.getData().get("userAddedTrial");
                            Date date = (Date) doc.getTimestamp("date").toDate();
                            User addedUser = new User(id);

                            if (experiment.getLocationRequired()) {
                                Double longitude = (Double) doc.getData().get("longitude");
                                Double latitude = (Double) doc.getData().get("latitude");

                                Location location = new Location(0, 0);

                                experiment.addTrial( new BinomialTrial(addedUser, location, date, success));
                            } else {
                                experiment.addTrial( new BinomialTrial(addedUser, date, success));
                            }
                        }
                        else if (experiment.getTrialType().equals("measurement")){
                            Log.d(TAG, String.valueOf(doc.getData().get("measurement")));
                            Double result = (Double) doc.getData().get("measurement");
                            String id = (String) doc.getData().get("userAddedTrial");
                            Date date = (Date) doc.getTimestamp("date").toDate();
                            User addedUser = new User(id);

                            if (experiment.getLocationRequired()) {
                                Double longitude = (Double) doc.getData().get("longitude");
                                Double latitude = (Double) doc.getData().get("latitude");
                                Location location = new Location(latitude, longitude);

                                experiment.addTrial(new MeasurementTrial(addedUser, location, date,
                                        result));
                            } else {
                                experiment.addTrial(new MeasurementTrial(addedUser, date,
                                        result));
                            }
                        }
                        else if (experiment.getTrialType().equals("nonNegativeCount")) {
                            Log.d(TAG, String.valueOf(doc.getData().get("nonNegativeCount")));
                            Long count = (Long) doc.getData().get("nonNegativeCount");
                            String id = (String) doc.getData().get("userAddedTrial");
                            Date date = (Date) doc.getTimestamp("date").toDate();
                            User addedUser = new User(id);

                            if (experiment.getLocationRequired()) {
                                Double longitude = (Double) doc.getData().get("longitude");
                                Double latitude = (Double) doc.getData().get("latitude");
                                Location location = new Location(latitude, longitude);

                                experiment.addTrial(new NonNegativeCountTrial(addedUser, location, date,
                                        count.intValue()));
                            } else {
                                experiment.addTrial(new NonNegativeCountTrial(addedUser, date,
                                        count.intValue()));
                            }
                        }

                    }
                    trialList.setTrial(experiment);

                } else {
                    Log.d(TAG, "No such collection");
                }

            }
        });

    }


    interface SetIgnoredList {
        void setIgnore(Experiment experiment);
    }

    public void getIgnoredList(Experiment experiment, SetIgnoredList ignoredList) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> col = db.collection("Users/" + experiment.getExperimentOwnerID() + "/OwnedExperiments/" + experiment.getDescription() + "/IgnoredExperimenters").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete (@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot doc : documents) {
                        String ignoredUser = doc.getId();
                        experiment.addIgnoredUser(new User(ignoredUser));
                    }
                    ignoredList.setIgnore(experiment);
                } else {
                    Log.d(TAG, "No such collection");
                }

            }
        });
    }

}