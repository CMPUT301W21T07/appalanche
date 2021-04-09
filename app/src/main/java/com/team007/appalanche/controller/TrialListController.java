package com.team007.appalanche.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TrialListController {
    private Experiment experiment;
    FirebaseFirestore db;

    public TrialListController(Experiment experiment) {
        this.experiment = experiment;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void clearTrialList() {
        experiment.getTrials().clear();
    }

    public void addTrial(Trial newTrial) {
        experiment.addTrial(newTrial);
    }

    public void addCountTrialToDb(Trial newTrial) {
        db = FirebaseFirestore.getInstance();
//        final DocumentReference docRef = db.collection("Experiments").document(experiment.getDescription());
//        docRef.update("trialList", FieldValue.arrayUnion(newTrial));

        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Trials");
        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, Object> data = new HashMap<>();
        data.put("userAddedTrial", newTrial.getUserAddedTrial().getId());
        data.put("date", newTrial.getDate());

        if (experiment.getLocationRequired()) {
            data.put("longitude", newTrial.getLocation().getLon());
            data.put("latitude", newTrial.getLocation().getLat());
        }

        collectionReference
                .document()
                .set(data);

        // ADD TO OWNER
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials/").document();
        document.set(data);
    }

    public void addBinomialTrialToDb(Trial newTrial) {
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription() + "/Trials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("binomial", ((BinomialTrial) newTrial).getOutcome());
        data.put("userAddedTrial", newTrial.getUserAddedTrial().getId());

        data.put("date", newTrial.getDate());

        if (experiment.getLocationRequired()) {
            data.put("longitude", newTrial.getLocation().getLon());
            data.put("latitude", newTrial.getLocation().getLat());
        }

        collectionReference
                .document()
                .set(data);
        //ADD TO OWNER LIST
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials/").document();
        document.set(data);
    }

    public void addMeasurementTrialToDb(Trial newTrial) {
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription() + "/Trials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("measurement", ((MeasurementTrial) newTrial).getValue());
        data.put("userAddedTrial", newTrial.getUserAddedTrial().getId());

        data.put("date", newTrial.getDate());

        if (experiment.getLocationRequired()) {
            data.put("longitude", newTrial.getLocation().getLon());
            data.put("latitude", newTrial.getLocation().getLat());
        }

        collectionReference
                .document()
                .set(data);
        //ADD TO OWNER LIST
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials/").document();
        document.set(data);
    }

    public void addNonNegTrialToDb(Trial newTrial) {
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription() + "/Trials");
        HashMap<String, Object> data = new HashMap<>();
        data.put("nonNegativeCount", ((NonNegativeCountTrial) newTrial).getCount());
        data.put("userAddedTrial", newTrial.getUserAddedTrial().getId());

        data.put("date", newTrial.getDate());

        if (experiment.getLocationRequired()) {
            data.put("longitude", newTrial.getLocation().getLon());
            data.put("latitude", newTrial.getLocation().getLat());
        }

        collectionReference
                .document()
                .set(data);

        //ADD TO OWNER LIST
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials/").document();
        document.set(data);
    }


    public void deleteTrials(User ignoreUser) {
        db = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> collectionReference = db.collection("Experiments/" + experiment.getDescription() + "/Trials").get().addOnCompleteListener
                (new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete (@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documents) {
                        Log.d(TAG, document.getId());
                        String userID = (String) document.getData().get("userAddedTrial");
                        if (ignoreUser.getId().matches(userID)) {
                            final CollectionReference col = db.collection("Experiments/" + experiment.getDescription() + "/Trials");
                            col.document(document.getId()).delete();
//                            //DELETE IN OWNER LIST
//                            final CollectionReference ownerCol = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials");
//                            ownerCol.document(document.getId()).delete();
                        }
                    }
                } else {
                    Log.d(TAG, "No such collection");
                }
            }
        });


        db.collection("Users/"+ experiment.getExperimentOwnerID() + "/OwnedExperiments/" + experiment.getDescription() + "/Trials")
                .get().addOnCompleteListener
                (new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete (@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                Log.d(TAG, document.getId());
                                String userID = (String) document.getData().get("userAddedTrial");
                                if (ignoreUser.getId().matches(userID)) {
                                    final CollectionReference ownerCol = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials");
                                    ownerCol.document(document.getId()).delete();
                                }
                            }
                        } else {
                            Log.d(TAG, "No such collection");
                        }
                    }
                });

    }
}
