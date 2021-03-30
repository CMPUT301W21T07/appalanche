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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

import java.io.Serializable;
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

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);
        User currentUser = new User(userKey);

        db = FirebaseFirestore.getInstance();

        experimentController = new ExperimentController(currentUser);

        // SET UP REAL TIME DATABASE FOR SUBSCRIBED EXPERIMENT LIST
        final CollectionReference collection = db.collection("Users/"+currentUser.getId()+"/SubscribedExperiments");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list, because we're not appending here, we load the whole list over again
                experimentController.clearSubscribedList();
                // for every document in this collection path
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String description = doc.getId();
                    // go to PUBLIC EXPERIMENTS collection and collect the document that has the same document ID
                    DocumentReference newDoc = db.collection("Experiments").document(description);
                    newDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete (@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.getData() != null) {
                                    Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                                    String description = document.getId();
                                    String trialType = (String) document.getData().get("trialType");
                                    Boolean expOpen =  document.getBoolean("expOpen");
                                    Long minNumTrials  = (Long)    document.getData().get("minNumTrials");
                                    String expOwnerID = (String) document.getData().get("expOwnerID");
                                    String region = (String) document.getData().get("region");
                                    Boolean locationRequired =  document.getBoolean("locationRequired");
                                    Experiment newExp = new Experiment(description, region, trialType, minNumTrials.intValue(),locationRequired, expOpen, expOwnerID );
                                    // add to SUBSCRIBED EXPERIMENT LIST
                                    experimentController.addSubscribedExperiment(newExp);
                                    expAdapter.notifyDataSetChanged();
                                } else {
                                    Log.d(TAG, "No such document");
                                    collection.document(description).delete();
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
                expAdapter.notifyDataSetChanged();
            }});


        //experimentController = new ExperimentController(currentUser);
//        experimentController.setCurrentUser(currentUser);
//        experimentController.setExperimentType(index);
        //experimentController.loadExperiments();

        // TEST
        // experimentController.addExperiment(new Experiment("How many jelly mans can a jelly bean fit in its mouth", "Edmonton", "NonNegative", 4, false, true, "123"));
        // experimentController.addExperiment(new Experiment(userKey, "Edmonton", "NonNegative", 4, false, true, "123"));
        //experimentController.addExperiment(new Experiment("How many jelly mans can a jelly bean fit in its mouth", "Edmonton", "NonNegative", 4, false, true, "123"), index);
        //experimentController.addSubExperiment(new Experiment("1234", "Edmonton", "NonNegative", 4, false, true, "123"));

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
        intent.putExtra("User", experimentController.getCurrentUser());
        startActivityForResult(intent,1);
    }

}