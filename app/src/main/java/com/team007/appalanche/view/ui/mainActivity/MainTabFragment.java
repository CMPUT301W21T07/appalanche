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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AddExperimentFragment;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    public static ExperimentController experimentController;
     ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    FirebaseFirestore db;

    public static MainTabFragment newInstance(int index) {
        MainTabFragment fragment = new MainTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //String userKey = getResources().getString(R.string.saved_user_key);
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
        User currentUser = new User(userKey);

        db = FirebaseFirestore.getInstance();
        //final CollectionReference ownedCol = db.collection("Users/"+currentUser.getId()+"/OwnedExperiments");
        final CollectionReference ownedCol = db.collection("Experiments");
        ownedCol.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                currentUser.getOwnedExperiments().clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String description = doc.getId();
                    String trialType = (String) doc.getData().get("trialType");
                    Boolean expOpen =  doc.getBoolean("expOpen");
                    Long minNumTrials      = (Long)    doc.getData().get("minNumTrials");
                    String expOwnerID = (String) doc.getData().get("expOwnerID");
                    ArrayList<Trial> trialList = (ArrayList<Trial>) doc.getData().get("trialList");
                    Experiment newExp = new Experiment(description, "AB", trialType, minNumTrials.intValue(),true, expOpen, expOwnerID );
                    //newExp.setTrials(trialList);
                    currentUser.addOwnedExperiment(newExp);

                }
                expAdapter.notifyDataSetChanged();
            }});


        experimentController = new ExperimentController(currentUser);
        experimentController.setCurrentUser(currentUser);
        experimentController.setExperimentType(index);
        //experimentController.loadExperiments();

        // TEST
        experimentController.addExperiment(new Experiment("How many jelly mans can a jelly bean fit in its mouth", "Edmonton", "NonNegative", 4, false, true, "123"));


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
                openExperimentActivity(experiment);
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
        startActivity(intent);
        startActivityForResult(intent,1);
    }

//    @Override
//    public void addExperiment(Experiment newExp) {
//        experimentController.addExperiment(newExp);
//    }
}