package com.team007.appalanche.controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
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

    public void addExperiment(Experiment experiment) {
        db = FirebaseFirestore.getInstance();
        //final CollectionReference ownedCol = db.collection("Users/"+currentUser.getId()+"/OwnedExperiments");
        final CollectionReference ownedCol = db.collection("Experiments");
        currentUser.addOwnedExperiment(experiment);
        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, Object> data = new HashMap<>();
        data.put("trialType", experiment.getTrialType());
        data.put("expOwnerID", experiment.getExperimentOwnerID());
        data.put("expOpen",experiment.getStatus());
        data.put("minNumTrials", experiment.getMinNumTrials());
        data.put("region",experiment.getRegion());
        data.put("locationRequired",experiment.getLocationRequired());
        experiment.addTrial(new CountBasedTrial(new User(), new Date(), 5));
        data.put("trialList", experiment.getTrials());

        ownedCol
                .document(experiment.getDescription())
                .set(data);

//        //SET UP THE COLLECTION FOR QUESTIONS BEFOREHAND
//        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Questions");
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    if(task.getResult().size() == 0) {
//                        HashMap<String, Object> data1 = new HashMap<>();
//                        collectionReference.add(data1);
//
//                    }
//                }
//            }
//        });
//        HashMap<String, Object> data1 = new HashMap<>();
//        collectionReference.add(data1);

    }
}
