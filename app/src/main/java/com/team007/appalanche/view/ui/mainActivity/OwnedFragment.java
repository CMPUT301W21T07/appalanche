package com.team007.appalanche.view.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.team007.appalanche.controller.ExperimentController;
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
 * A placeholder fragment containing a simple view.
 */
public class OwnedFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static ExperimentController experimentController;
    public ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    FirebaseFirestore db;
    private User currentUser;

    public static OwnedFragment newInstance(int index) {
        OwnedFragment fragment = new OwnedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
        currentUser = new User(userKey);

        experimentController = new ExperimentController(currentUser);
        setUpFirebase(currentUser);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mainpage_tab_fragment, container, false);

        // Obtain the IDs
        expList = root.findViewById(R.id.expList);
        ExperimentDataList = experimentController.getCurrentUser().getOwnedExperiments();

        // Set up the adapter for Experiment List View
        expAdapter = new CustomList(this.getActivity(), ExperimentDataList);
        expList.setAdapter(expAdapter);

        // Open the experiment
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Experiment experiment = ExperimentDataList.get(position);
                getIgnoredList(experiment, new SetIgnoredList() {
                    @Override
                    public void setIgnore(Experiment experiment) {
                        //Toast.makeText(getContext(), String.valueOf(experiment.getIgnoredUsers().size()), Toast.LENGTH_SHORT).show();
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

        return root;
    }

    /**
     * This opens the experiment activity.
     * @param experiment This is the experiment activity to open.
     */
    private void openExperimentActivity(Experiment experiment) {
        Intent intent = new Intent(getContext(), ExperimentActivity.class);
        intent.putExtra("Experiment", experiment);
        intent.putExtra("User", experimentController.getCurrentUser());
        startActivityForResult(intent,1);
    }

    public void setUpFirebase(User currentUser) {
        db = FirebaseFirestore.getInstance();

        // Set up realtime changes for UI
        // (Anything changes in user's owned experiments collection path, UI changes)
        final CollectionReference collection = db.collection("Users/"+ currentUser.getId() +
                "/OwnedExperiments");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list, because we're not appending here, we load the whole list over again
                experimentController.clearOwnedList();

                // For every document in this collection path
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String description = doc.getId();
                    String trialType = (String) doc.getData().get("trialType");
                    Boolean expOpen =  doc.getBoolean("expOpen");
                    Long minNumTrials  = (Long) doc.getData().get("minNumTrials");
                    String expOwnerID = (String) doc.getData().get("expOwnerID");
                    String region = (String) doc.getData().get("region");
                    Boolean locationRequired =  doc.getBoolean("locationRequired");

                    Experiment newExp = new Experiment(description, region, trialType,
                            minNumTrials.intValue(), locationRequired, expOpen, expOwnerID );
                    // add to owned experiment in the user owned list
                    //if (experimentController.getCurrentUser().getSubscribedExperiments().contains(newExp) == false )
                    experimentController.addOwnExperiment(newExp);
                }

                expAdapter.notifyDataSetChanged();
            }});
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

//        final CollectionReference collection =
//                db.collection("Users/" + experiment.getExperimentOwnerID() + "/OwnedExperiments/" + experiment.getDescription() + "/IgnoredExperimenters");
//
//        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
//                                @Nullable FirebaseFirestoreException e) {
//                experiment.getIgnoredUsers().clear();
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
//                    Log.d(TAG, String.valueOf(doc.getData().get("add ignored experimenters")));
//                    String ignoredUser = doc.getId();
//                    experiment.addIgnoredUser(new User(ignoredUser));
//                }
//                ignoredList.setIgnore(experiment);
//            }
//        });
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

//        final CollectionReference ownedCol = db.collection("Experiments/"+experiment.getDescription()+"/Trials");
//        ownedCol.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                // clear the old list
//                experiment.getTrials().clear();
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
//                    if (experiment.getTrialType().equals("count")) {
//                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
//                        String id = (String) doc.getData().get("userAddedTrial");
//                        Date date = (Date) doc.getTimestamp("date").toDate();
//                        User addedUser = new User(id);
//
//                        if (experiment.getLocationRequired()) {
//                            Double longitude = (Double) doc.getData().get("longitude");
//                            Double latitude = (Double) doc.getData().get("latitude");
//                            Location location = new Location(latitude, longitude);
//
//                            experiment.addTrial( new CountBasedTrial(addedUser, location, date));
//                        } else {
//                            experiment.addTrial( new CountBasedTrial(addedUser, date));
//                        }
//                    }
//                    else if (experiment.getTrialType().equals("binomial")){
//                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
//                        Boolean success= (Boolean) doc.getData().get("binomial");
//                        String id = (String) doc.getData().get("userAddedTrial");
//                        Date date = (Date) doc.getTimestamp("date").toDate();
//                        User addedUser = new User(id);
//
//                        if (experiment.getLocationRequired()) {
//                            Double longitude = (Double) doc.getData().get("longitude");
//                            Double latitude = (Double) doc.getData().get("latitude");
//
//                            Location location = new Location(0, 0);
//
//                            experiment.addTrial( new BinomialTrial(addedUser, location, date, success));
//                        } else {
//                            experiment.addTrial( new BinomialTrial(addedUser, date, success));
//                        }
//                    }
//                    else if (experiment.getTrialType().equals("measurement")){
//                        Log.d(TAG, String.valueOf(doc.getData().get("measurement")));
//                        Double result = (Double) doc.getData().get("measurement");
//                        String id = (String) doc.getData().get("userAddedTrial");
//                        Date date = (Date) doc.getTimestamp("date").toDate();
//                        User addedUser = new User(id);
//
//                        if (experiment.getLocationRequired()) {
//                            Double longitude = (Double) doc.getData().get("longitude");
//                            Double latitude = (Double) doc.getData().get("latitude");
//                            Location location = new Location(latitude, longitude);
//
//                            experiment.addTrial(new MeasurementTrial(addedUser, location, date,
//                                    result));
//                        } else {
//                            experiment.addTrial(new MeasurementTrial(addedUser, date,
//                                    result));
//                        }
//                    }
//                    else if (experiment.getTrialType().equals("nonNegativeCount")) {
//                        Log.d(TAG, String.valueOf(doc.getData().get("nonNegativeCount")));
//                        Long count = (Long) doc.getData().get("nonNegativeCount");
//                        String id = (String) doc.getData().get("userAddedTrial");
//                        Date date = (Date) doc.getTimestamp("date").toDate();
//                        User addedUser = new User(id);
//
//                        if (experiment.getLocationRequired()) {
//                            Double longitude = (Double) doc.getData().get("longitude");
//                            Double latitude = (Double) doc.getData().get("latitude");
//                            Location location = new Location(latitude, longitude);
//
//                            experiment.addTrial(new NonNegativeCountTrial(addedUser, location, date,
//                                    count.intValue()));
//                        } else {
//                            experiment.addTrial(new NonNegativeCountTrial(addedUser, date,
//                                    count.intValue()));
//                        }
//                    }
//                }
//                trialList.setTrial(experiment);
//            }});

    }
}