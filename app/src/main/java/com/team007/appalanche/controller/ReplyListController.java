package com.team007.appalanche.controller;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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

    public void addReplyToDb(Reply newReply, CollectionReference collectionReference) {

        //collectionReference.add(newQuestion.getContent());
        // We use a HashMap to store a key-value pair in firestore.
        HashMap<String, String> data = new HashMap<>();
        data.put("content", newReply.getReplyText());
        //data.put("userPostedReply", newReply.getUserReplied().getId());
        collectionReference
                .document(newReply.getReplyText())
                .set(data);

    }
}
