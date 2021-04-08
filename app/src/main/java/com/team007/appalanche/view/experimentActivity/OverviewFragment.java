package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.TrialListController;
import com.team007.appalanche.experiment.CountBasedExperiment;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class OverviewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Experiment experiment;

    public static OverviewFragment newInstance(int index) {
        OverviewFragment fragment = new OverviewFragment();
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
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_overview, container, false);

        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());

        TextView owner = root.findViewById(R.id.owner);
        owner.setText("Owner: " + experiment.getExperimentOwnerID());

        // If user click on owner ID, go to the owner profile page
        viewAProfile(owner);

        TextView status = root.findViewById(R.id.status);
        if (experiment.getOpen()) {
            status.setText("Status: Open");
        } else {
            status.setText("Status: Closed");
        }

        TextView region = root.findViewById(R.id.region);
        region.setText("Region:" + experiment.getRegion());

        TextView currentTrialNum = root.findViewById(R.id.currentNumbTrials);
        currentTrialNum.setText("Current number of trials: " + experiment.getTrials().size());

        TextView minTrialNum = root.findViewById(R.id.minTrials);
        minTrialNum.setText("Minimum number of trials: " + experiment.getMinNumTrials().toString());

        // SET UP HISTOGRAM HERE
        setUpFirebase();
        GraphView histogram = root.findViewById(R.id.histogram);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint());
        histogram.addSeries(series);


        return root;
    }

    public void viewAProfile(TextView owner) {
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                String string = owner.getText().toString();
                String userID = (String) string.subSequence(7, string.length());
                intent.putExtra("Profile", new User(userID));
                startActivity(intent);
            }
        });
    }


    public void histogram() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private DataPoint[] getDataPoint() {
        CountBasedExperiment exp = (CountBasedExperiment) experiment;
        ArrayList<Integer> countList = exp.obtainHistogram();
        DataPoint[] series = new DataPoint[] {
                new DataPoint(countList.get(0), 1),
                new DataPoint(countList.get(1), 5),
                new DataPoint(countList.get(2), 3)
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
        };
        return series;
    }


    public void setUpFirebase() {
        //set up firebase, realtime updates
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference ownedCol = db.collection("Experiments/"+experiment.getDescription()+"/Trials");
        //final CollectionReference ownedCol = db.collection("Users/"+user.getId()+"/OwnedExperiments/"+experiment.getDescription()+"/Trials");
        ownedCol.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                experiment.getTrials().clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){

                    if (experiment.getTrialType().equals("count")) {
                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
                        Long count = (Long) doc.getData().get("count");
                        String id = (String) doc.getData().get("userAddedTrial");
                        User addedUser = new User(id);
                        experiment.addTrial( new CountBasedTrial(addedUser, new Date(), count.intValue()));
                    }
                    else if (experiment.getTrialType().equals("binomial")){
                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
                        Boolean success= (Boolean) doc.getData().get("binomial");
                        String id = (String) doc.getData().get("userAddedTrial");
                        User addedUser = new User(id);
                        experiment.addTrial(new BinomialTrial(addedUser, new Date(),success));
                    }
                    else if (experiment.getTrialType().equals("measurement")){
                        Log.d(TAG, String.valueOf(doc.getData().get("measurement")));
                        Double result = (Double) doc.getData().get("measurement");
                        String id = (String) doc.getData().get("userAddedTrial");
                        User addedUser = new User(id);
                        experiment.addTrial(new MeasurementTrial(addedUser, new Date(), result));
                    }
                    else if (experiment.getTrialType().equals("nonNegativeCount")) {
                        Log.d(TAG, String.valueOf(doc.getData().get("nonNegativeCount")));
                        Long count = (Long) doc.getData().get("nonNegativeCount");
                        String id = (String) doc.getData().get("userAddedTrial");
                        User addedUser = new User(id);
                        experiment.addTrial(new NonNegativeCountTrial(addedUser, new Date(), count.intValue()));
                    }
                }
            }});
    }
}