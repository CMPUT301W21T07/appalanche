package com.team007.appalanche.controller;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;

import java.util.ArrayList;
import java.util.HashMap;

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
        data.put("count", ((CountBasedTrial) newTrial).getCount());
        data.put("userAddedTrial", newTrial.getUserAddedTrial());
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
        data.put("userAddedTrial", newTrial.getUserAddedTrial());
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
        data.put("userAddedTrial", newTrial.getUserAddedTrial());
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
        data.put("userAddedTrial", newTrial.getUserAddedTrial());
        collectionReference
                .document()
                .set(data);
        //ADD TO OWNER LIST
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Trials/").document();
        document.set(data);
    }

//    public void ignoreTrial(Trial trialBeingIgnored) {
//        db = FirebaseFirestore.getInstance();
//        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Trials");
//        collectionReference
//                .document()
//                .set(data);
//    }
}
