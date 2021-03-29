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
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class SubscribedFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    public static ExperimentController experimentController;
    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;
    FirebaseFirestore db;
    public int index;

    public static SubscribedFragment newInstance(int index) {
        SubscribedFragment fragment = new SubscribedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        index = 1;
//        Toast.makeText(getActivity(), "Oops, you didn't scan anything",
//                Toast.LENGTH_LONG).show();
//
//        if (getArguments() != null) {
////            Toast.makeText(getActivity(), "Oops, you didn't scan anything",
////                    Toast.LENGTH_LONG).show();
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        else
//            Toast.makeText(getActivity(), "Oops, you didn't scan anything",
//                    Toast.LENGTH_LONG).show();
//
//        Log.d(TAG, String.valueOf(index));
        //index = 1;
        //System.out.println(String.valueOf(index));
        // INDEX 1: Owned, INDEX 2: subscribed
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
        User currentUser = new User(userKey);

        db = FirebaseFirestore.getInstance();
        //final CollectionReference ownedCol = db.collection("Users/"+currentUser.getId()+"/OwnedExperiments");
//        CollectionReference collection = db.collection("Experiments");
//        if (index == 2)
           final CollectionReference collection = db.collection("Subscribed");


        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                currentUser.getOwnedExperiments().clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String description = doc.getId();
                    String trialType = (String) doc.getData().get("trialType");
                    Boolean expOpen =  doc.getBoolean("expOpen");
                    //Long minNumTrials      = (Long)    doc.getData().get("minNumTrials");
                    String expOwnerID = (String) doc.getData().get("expOwnerID");

                    //Experiment newExp = new Experiment(description, "AB", trialType, minNumTrials.intValue(),true, expOpen, expOwnerID );
                    Experiment newExp = new Experiment(description, "AB", trialType, 4,true, expOpen, expOwnerID );
                    //newExp.setTrials(trialList);
//                    if (index == 1)
//                        currentUser.addOwnedExperiment(newExp);
//                    else if (index == 2)
                        currentUser.addSubscribedExperiment(newExp);
                }
                expAdapter.notifyDataSetChanged();
            }});


        experimentController = new ExperimentController(currentUser);
        experimentController.setCurrentUser(currentUser);
        experimentController.setExperimentType(index);
        //experimentController.loadExperiments();

        // TEST
        // experimentController.addExperiment(new Experiment("How many jelly mans can a jelly bean fit in its mouth", "Edmonton", "NonNegative", 4, false, true, "123"));
        // experimentController.addExperiment(new Experiment(userKey, "Edmonton", "NonNegative", 4, false, true, "123"));
        //experimentController.addExperiment(new Experiment("How many jelly mans can a jelly bean fit in its mouth", "Edmonton", "NonNegative", 4, false, true, "123"), index);
        //experimentController.addExperiment(new Experiment(String.valueOf(index), "Edmonton", "NonNegative", 4, false, true, "123"), index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mainpage_tab_fragment, container, false);

        Toast.makeText(getActivity(), "Oops, you didn't scan anything",
                Toast.LENGTH_LONG).show();
        // Obtain the IDs
        expList = root.findViewById(R.id.expList);
//        if ( index == 1 )
//            ExperimentDataList = experimentController.getCurrentUser().getOwnedExperiments();
//        else if (index == 2)
            ExperimentDataList = experimentController.getCurrentUser().getSubscribedExperiments();

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
        startActivityForResult(intent,1);
    }

//    @Override
//    public void addExperiment(Experiment newExp) {
//        experimentController.addExperiment(newExp);
//    }
}