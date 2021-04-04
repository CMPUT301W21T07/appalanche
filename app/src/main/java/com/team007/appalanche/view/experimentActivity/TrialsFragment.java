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
import com.team007.appalanche.R;
import com.team007.appalanche.controller.TrialListController;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.custom.TrialCustomList;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.ContactInfo;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddMeasurementTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddNonNegTrialFragment;
import com.team007.appalanche.view.profile.ProfileActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrialsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView trialListView;
    private ArrayAdapter<Trial> trialAdapter;
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


//        CREATE trialController here
        trialListController = new TrialListController(experiment);


        //set up firebase, realtime updates
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
                        Long count = (Long) doc.getData().get("count");
                        trialListController.addTrial( new CountBasedTrial(user, new Date(), count.intValue()));
                    }

                    if (experiment.getTrialType().equals("binomial")){
                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
                        Long count = (Long) doc.getData().get("count");
                        trialListController.addTrial(new BinomialTrial(user, new Date(), true));
                    }

//                    if (experiment.getTrialType().equals("measurement")) {
//                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
//                        Float count = (Long) doc.getData().get("count");
//                        trialListController.addTrial(new MeasurementTrial(user, new Date(), count.floatValue()));
//                    }
//
//                    if (experiment.getTrialType().equals("nonNegMeasurement")) {
//                        Log.d(TAG, String.valueOf(doc.getData().get("description")));
//                        Long count = (Long) doc.getData().get("count");
//                        trialListController.addTrial(new NonNegativeCountTrial(user, new Date(), count.intValue()));
//                    }
                }
                trialAdapter.notifyDataSetChanged();
            }});
//

//        //TEST
        //trialListController.addTrialToDb( new CountBasedTrial(new User(), new Date(), 4));
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_trials, container, false);

        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());


        Button addTrialButton = root.findViewById(R.id.addTrialButton);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTrialActivity();
            }
        });

        // SET UP TRIAL LISTVIEW

        trialDataList = trialListController.getExperiment().getTrials();
        trialAdapter = new TrialCustomList(this.getContext(), trialDataList);
        trialListView = root.findViewById(R.id.trialList);
        trialListView.setAdapter(trialAdapter);

        // ON CLICK USER'S ID
        viewAProfile();

//        // IGNORE CERTAIN TRIAL
//        trialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        return root;
    }

    public void openAddTrialActivity() {
        switch(experiment.getTrialType()) {
            case "binomial":
                new AddBinomialTrialFragment().newInstance(user).show(getFragmentManager(), "Add_Trial");
                break;
            case "count":
                new AddCountTrialFragment().newInstance(user).show(getFragmentManager(), "Add_Trial");
                break;
            case "measurement":
                new AddMeasurementTrialFragment().newInstance(user).show(getFragmentManager(), "Add_trial");
                break;
            case "nonNegativeCount":
                new AddNonNegTrialFragment().newInstance(user).show(getFragmentManager(), "Add_Trial");
            default:
                break;
        }
        // TODO: hook fragment result to update experiment and create trial
    }

    public void viewAProfile() {
        trialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trial trial = trialDataList.get(position);
                String userID = trial.getUserAddedTrial().getId();
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("Profile", new User(userID));
                startActivityForResult(intent,1);
                // FIX BACK BUTTON
            }
        });
    }

}