package com.team007.appalanche.controller;

import android.app.ActivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This controller serves as an interface between the model for creating questions and the
 * UI for viewing questions. When a user requests the creation of a question, their message
 * is routed to this controller and added to that current experiment's question-list.
 */

public class QuestionListController {
    private Experiment experiment;
    FirebaseFirestore db;

    public QuestionListController(Experiment experiment) {
        this.experiment = experiment;
    }

    public Experiment getExperiment() {
        return experiment;
    }


    public void clearQuestionList() {
        experiment.getQuestions().clear();
    }

    public void addQuestion(Question newQuestion) {
        experiment.addQuestion(newQuestion);
    }

    public void addQuestionToDb(Question newQuestion) {
        //questionList.add(newQuestion);
        db = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Questions");
        // collectionReference.add(newQuestion.getContent());
        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, String> data = new HashMap<>();
        data.put("user_posted_question", newQuestion.getUserPostedQuestion().getId());
        collectionReference
                .document(newQuestion.getContent())
                .set(data);

//        // SET UP COLLECTION FOR REPLIES BEFOREHAND
//        final CollectionReference collectionReference1 = db.collection("Experiments/" + experiment.getDescription()+"/Questions/"+newQuestion.getContent()+"/Replies");
//        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    if(task.getResult().size() == 0) {
//                        HashMap<String, Object> data1 = new HashMap<>();
//                        collectionReference1.add(data1);
//
//                    }
//                }
//
//            }
//        });
//        HashMap<String, Object> data1 = new HashMap<>();
//        collectionReference1.add(data1);
    }
}
