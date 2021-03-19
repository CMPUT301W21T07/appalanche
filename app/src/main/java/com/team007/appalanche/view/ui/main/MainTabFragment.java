package com.team007.appalanche.view.ui.main;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.User.User;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.view.ExperimentActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ExperimentController experimentController;

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
        //experimentController = new ViewModelProvider(this).get(ExperimentController1.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
//
//
//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        //String userKey = getResources().getString(R.string.saved_user_key);
//        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
//
//        experimentController.setCurrentUser(userKey);
//        experimentController.setExperimentType(index);
//        experimentController.loadExperiments();


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
        User currentUser = new User(userKey);
        //currentUser.addOwnedExperiment(new Experiment("Hello"));

        db = FirebaseFirestore.getInstance();
        final CollectionReference ownedCol = db.collection("Users/"+currentUser.getId()+"/OwnedExperiments");
        //final CollectionReference ownedCol = db.collection("Experiments");
        ownedCol.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                currentUser.getOwnedExperiments().clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String exp = doc.getId();
                    String description = (String) doc.getData().get("description");
                    currentUser.addOwnedExperiment(new Experiment(exp));
                    currentUser.addOwnedExperiment(new Experiment("Hi"));

                }
                expAdapter.notifyDataSetChanged();
            }});

        experimentController = new ExperimentController(currentUser);
        experimentController.setExperimentType(index);
        //experimentController.loadExperiments();
        expAdapter = new CustomList(this.getActivity(), ExperimentDataList);
        expAdapter.notifyDataSetChanged();

        //TEST
        experimentController.addExperiment(new Experiment("heLLO"));

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mainpage_tab_fragment, container, false);

        // Obtain the IDs
        expList = root.findViewById(R.id.expList);

        // Load the experiments
        ExperimentDataList = experimentController.getCurrentUser().getOwnedExperiments();
//        String context = ExperimentDataList.get(0).getDescription();
//        Toast.makeText(getActivity(), context, Toast.LENGTH_LONG).show();

//        ExperimentDataList = new ArrayList<Experiment>();
//        ExperimentDataList.add(new Experiment("Hello"));
        // Set up the adapter for Experiment List View
        expAdapter = new CustomList(this.getActivity(), ExperimentDataList);
        expList.setAdapter(expAdapter);

        // Open the experiment
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openExperimentActivity();
            }
        });

        return root;
    }

    public void openExperimentActivity() {
        Intent intent = new Intent(this.getActivity(), ExperimentActivity.class);
        startActivityForResult(intent,1);
    }
}