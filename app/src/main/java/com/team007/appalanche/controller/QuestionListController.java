package com.team007.appalanche.controller;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;

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
        HashMap<String, Object> data = new HashMap<>();
        data.put("user_posted_question", newQuestion.getUserPostedQuestion().getId());
        data.put("date", newQuestion.getDateAsked());
        collectionReference
                .document(newQuestion.getContent())
                .set(data);
        // ADD TO OWNER
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Questions/").document(newQuestion.getContent());
        document.set(data);

    }
}
