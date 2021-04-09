package com.team007.appalanche.controller;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.question.Reply;

import java.util.HashMap;

/**
 * This controller stores replies to a chosen question in a chosen experiment.
 */

public class ReplyListController {
    private Question question;
    FirebaseFirestore db;
    public ReplyListController(Question question) {
        this.question = question;
    }

    public void clearReplyList() {
        question.getReplies().clear();
    }
    public Question getQuestion() {
        return question;
    }

    public void addReply(Reply newReply) {
        question.addReply(newReply);
    }

    public void addReplyToDb(Reply newReply, Experiment experiment) {

        db = FirebaseFirestore.getInstance();
        // Get a top-level reference to the collection.
        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Questions/"+question.getContent()+"/Replies");
        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, Object> data = new HashMap<>();
        //data.put("content", newReply.getReplyText());
        data.put("userPostedReply", newReply.getUserReplied().getId());
        data.put("date", newReply.getDateReplied());
        collectionReference
                .document(newReply.getReplyText())
                .set(data);

        // ADD TO OWNER COLLECTION
        final DocumentReference document = db.collection("Users/"+ experiment.getExperimentOwnerID() +"/OwnedExperiments/"+ experiment.getDescription()+"/Questions/"+question.getContent()+"/Replies").document(newReply.getReplyText());
        document.set(data);
    }
}
