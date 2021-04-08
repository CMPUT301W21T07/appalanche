package com.team007.appalanche.controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;
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

        // ADD EXPERIMENT TO OWNED LIST EXPERIMENT INSIDE USER BECAUSE WE WANT TO KEEP THE EXPERIMENT WHEN UNPUBLISH EXPERIMENT
        final CollectionReference ownerCol = db.collection("Users/"+currentUser.getId()+"/OwnedExperiments");
        ownerCol
                .document(experiment.getDescription())
                .set(data);
    }
    public void addSubExperiment(Experiment experiment) {
        db = FirebaseFirestore.getInstance();
        // add a document key to subscribedExperiment collection
        final DocumentReference document = db.collection("Users/"+currentUser.getId()+"/SubscribedExperiments").document(experiment.getDescription());
        HashMap<String, Object> data = new HashMap<>();

        document.set(data);
    }

}
