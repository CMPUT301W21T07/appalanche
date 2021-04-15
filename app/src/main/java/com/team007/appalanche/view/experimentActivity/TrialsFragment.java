package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.Location;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.TrialListController;
import com.team007.appalanche.custom.TrialCustomList;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.profile.ProfileActivity;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddMeasurementTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddNonNegTrialFragment;

import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * A fragment containing the view rendered for the trials experiment tab.
 */
public class TrialsFragment extends Fragment  {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView trialListView;
    public static ArrayAdapter<Trial> trialAdapter;
    private ArrayList<Trial> trialDataList;
    private Experiment experiment;
    private User user;
    public static TrialListController trialListController;
    FirebaseFirestore db;

    public static TrialsFragment newInstance(int index) {
        TrialsFragment fragment = new TrialsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        experiment = (Experiment) intent.getSerializableExtra("Experiment");
        user = (User) intent.getSerializableExtra("User");

        // Create trialController here
        trialListController = new TrialListController(experiment);
        setUpFirebase();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_experiment_trials, container, false);

        ArrayList<Trial> trials = experiment.getTrials();

        // Set description text
        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());

        // Map Logic
        Button viewMapButton = root.findViewById(R.id.viewMapButton);

        // Trial logic
        Button addTrialButton = root.findViewById(R.id.addTrialButton);
        boolean inIgnoredList = checkIgnoredExperimenters();
        if (experiment.getOpen()) {
            // Adding an onClick listener if the current trial is still open
            addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IF THE CURRENT USER IS IN THE IGNORED LIST OF CURRENT EXPERIMENT -> SHOW MESSAGE
                if (inIgnoredList)
                    Toast.makeText(getContext(), "You're not allowed to add trial to this experiment", Toast.LENGTH_SHORT).show();
                else
                    openAddTrialActivity();
            }
        });
        } else {
            // Removing the add trial button if the current trial is ended
            addTrialButton.setVisibility(View.GONE);
        }

        // Set up Trial ListView
        trialDataList = trialListController.getExperiment().getTrials();
        trialAdapter = new TrialCustomList(this.getContext(), trialDataList, experiment.getTrialType());
        trialListView = root.findViewById(R.id.trialList);
        trialListView.setAdapter(trialAdapter);

        if (experiment.getLocationRequired()) {
            // Adding an onClick listener if the current trial is still open
            viewMapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openViewMapActivity();
                }
            });
        } else {
            // Removing the add trial button if the current trial is ended
            viewMapButton.setVisibility(View.GONE);
        }

        trialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                Trial trial = trialDataList.get(position);

                // View a user profile
                if (viewId == R.id.userID) {
                    viewAProfile(trial);
                }

                // Ignore a certain experimenter
                // If current User is the owner of the experiment
                // CHECK THIS AFTER SEARCHING IS DONE
                else if (viewId == R.id.ignoreUser && experiment.getExperimentOwnerID().matches(user.getId())) {
                    User ignoredUser = trial.getUserAddedTrial();
                    new IgnoreAUserFragment().newInstance(ignoredUser).show(getFragmentManager(), "Add_Ignored_User");
                }
            }
        });

        return root;
    }

    private void openViewMapActivity() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("Experiment", experiment);
        startActivity(intent);
    }

    public void openAddTrialActivity() {
        if (!experiment.getOpen()) {
            // Throw an exception if we're trying to add a trial to an ended experiment
            throw new RuntimeException("Cannot add a new trial as the experiment is ended");
        }

        switch(experiment.getTrialType()) {
            case "binomial":
                new AddBinomialTrialFragment().newInstance(user, experiment.getLocationRequired()).show(getFragmentManager(),
                        "Add_Trial");
                break;
            case "count":
                new AddCountTrialFragment().newInstance(user, experiment.getLocationRequired()).show(getFragmentManager(), "Add_Trial");
                break;
            case "measurement":
                new AddMeasurementTrialFragment().newInstance(user, experiment.getLocationRequired()).show(getFragmentManager(), "Add_trial");
                break;
            case "nonNegativeCount":
                new AddNonNegTrialFragment().newInstance(user, experiment.getLocationRequired()).show(getFragmentManager(), "Add_Trial");
            default:
                break;
        }
    }

    public void viewAProfile(Trial trial) {
        String userID = trial.getUserAddedTrial().getId();
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra("Profile", new User(userID));
        startActivity(intent);
    }

    public boolean checkIgnoredExperimenters() {
        boolean inIgnoredList = false;
        ArrayList<User> ignoredList = experiment.getIgnoredUsers();
        for( int i =0; i< ignoredList.size(); i++) {
            if(user.getId().equals(ignoredList.get(i).getId())) {
                inIgnoredList = true;
                break;
            }
        }
        return inIgnoredList;
    }


    public void setUpFirebase() {
        // Set up firebase, realtime updates
        db = FirebaseFirestore.getInstance();
        final CollectionReference ownedCol = db.collection("Experiments/"+experiment.getDescription()+"/Trials");

        ownedCol.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                trialListController.clearTrialList();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){

                    if (experiment.getTrialType().equals("count")) {
                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
                        String id = (String) doc.getData().get("userAddedTrial");
                        Date date = (Date) doc.getTimestamp("date").toDate();
                        User addedUser = new User(id);

                        if (experiment.getLocationRequired()) {
                            Double longitude = (Double) doc.getData().get("longitude");
                            Double latitude = (Double) doc.getData().get("latitude");
                            System.out.println(latitude);
                            System.out.println(longitude);
                            Location location = new Location(latitude, longitude);

                            trialListController.addTrial( new CountBasedTrial(addedUser, location, date));
                        } else {
                            trialListController.addTrial( new CountBasedTrial(addedUser, date));
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

                            Location location = new Location(latitude, longitude);

                            trialListController.addTrial( new BinomialTrial(addedUser, location, date, success));
                        } else {
                            trialListController.addTrial( new BinomialTrial(addedUser, date, success));
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

                            trialListController.addTrial(new MeasurementTrial(addedUser, location, date,
                                    result));
                        } else {
                            trialListController.addTrial(new MeasurementTrial(addedUser, date,
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
                            trialListController.addTrial(new NonNegativeCountTrial(addedUser, location, date,
                                    count.intValue()));
                        } else {
                            trialListController.addTrial(new NonNegativeCountTrial(addedUser, date,
                                    count.intValue()));
                        }
                    }
                }
                trialAdapter.notifyDataSetChanged();
            }});

    }
}