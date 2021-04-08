package com.team007.appalanche.controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void clearOwnedList() {
        currentUser.getOwnedExperiments().clear();
    }
    public void clearSubscribedList() {
        currentUser.getSubscribedExperiments().clear();
    }
    public void addOwnExperiment(Experiment experiment) {currentUser.addOwnedExperiment(experiment);}
    public void addSubscribedExperiment(Experiment experiment) {currentUser.addSubscribedExperiment(experiment);}

    public void endExperiment(Experiment experiment) {
        if (!experiment.getExperimentOwnerID().equals(currentUser.getId())) {
            throw new RuntimeException("Current user not authorized to end the experiment");
        }

        // Change the status in the model
        experiment.setOpen(false);

        db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Experiments");

        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, Object> data = new HashMap<>();
        data.put("expOpen", false);

        // ADD EXPERIMENT TO THE PUBLIC COLLECTION "EXPERIMENTS"
        collection
                .document(experiment.getDescription())
                .update(data);

        // Update experiment in owned user's list
        final CollectionReference ownerCol = db.collection("Users/" + currentUser.getId() +
                "/OwnedExperiments");
        ownerCol
                .document(experiment.getDescription())
                .update(data);
    }

    public void addExperiment(Experiment experiment) {
        db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Experiments");

        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, Object> data = new HashMap<>();
        data.put("trialType", experiment.getTrialType());
        data.put("expOwnerID", experiment.getExperimentOwnerID());
        data.put("expOpen",experiment.getOpen());
        data.put("minNumTrials", experiment.getMinNumTrials());
        data.put("region",experiment.getRegion());
        data.put("locationRequired",experiment.getLocationRequired());

        // ADD EXPERIMENT TO THE PUBLIC COLLECTION "EXPERIMENTS"
        collection
                .document(experiment.getDescription())
                .set(data);

        // Add experiment to onwer's owned list
        // (
        // ADD EXPERIMENT TO OWNED LIST EXPERIMENT INSIDE USER BECAUSE WE WANT TO KEEP THE EXPERIMENT WHEN UNPUBLISH EXPERIMENT
        final CollectionReference ownerCol = db.collection("Users/" + currentUser.getId() +
                "/OwnedExperiments");
        ownerCol
                .document(experiment.getDescription())
                .set(data);
    }

    public void addSubExperiment(Experiment experiment) {
        db = FirebaseFirestore.getInstance();
        // add a document key to subscribedExperiment collection
        final DocumentReference document = db.collection("Users/" + currentUser.getId() +
                "/SubscribedExperiments").document(experiment.getDescription());
        HashMap<String, Object> data = new HashMap<>();

        document.set(data);
    }

    public void unpublishExp(Experiment experiment) {
        // still in the owner list but not in the public experiment list
        db = FirebaseFirestore.getInstance();
        final DocumentReference document = db.collection("Experiments").document(experiment.getDescription());
        document
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Experiment successfully unpublished");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error unpublishing experiment", e);
                    }
                });

    }


}
