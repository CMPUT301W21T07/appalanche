package com.team007.appalanche.controller;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.User;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class ExperimentController {
    User currentUser;
    FirebaseFirestore db;
    private MutableLiveData<Integer> experimentType = new MutableLiveData<>();
    public ExperimentController(User currentUser) {
        this.currentUser = currentUser;
    };

    public void setExperimentType(int index) {
        experimentType.setValue(index);
    }

    public void loadExperiments() {
        //DocumentReference userDoc = db.collection("Users").document(currentUser.getId());
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
                    String exp = doc.getId();
                    String description = (String) doc.getData().get("description");

                    currentUser.addOwnedExperiment(new Experiment(exp));
                    currentUser.addOwnedExperiment(new Experiment("Hi"));

                }}});

        //        // Access a Cloud Firestore instance from your Activity
//        db = FirebaseFirestore.getInstance();
//        // Get a top-level reference to the collection.
//        final CollectionReference collectionReference = db.collection("Questions");
//
//        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//


    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void addExperiment(Experiment experiment) {
        db = FirebaseFirestore.getInstance();
        final CollectionReference ownedCol = db.collection("Users/"+currentUser.getId()+"/OwnedExperiments");

        currentUser.addOwnedExperiment(experiment);

        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, String> data = new HashMap<>();
        data.put("user_posted_question", experiment.getDescription());
        ownedCol
                .document("newExp")
                .set(data);
    }
}
