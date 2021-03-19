package com.team007.appalanche.controller;

import com.team007.appalanche.question.Question;

import java.util.ArrayList;

/**
 * This controller serves as an interface between the model for creating questions and the
 * UI for viewing questions. When a user requests the creation of a question, their message
 * is routed to this controller and added to that current experiment's question-list.
 */

public class QuestionListController {
    private ArrayList<Question> questionList = new ArrayList<Question>();

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void clearQuestionList() {
        questionList.clear();
    }

    public void addQuestion(Question newQuestion) {
        questionList.add(newQuestion);
    }

//    public void addQuestionToDb(Question newQuestion, FirebaseFirestore db) {
//        //questionList.add(newQuestion);
//
//        final CollectionReference collectionReference = db.collection("questions");
//        // We use a HashMap to store a key-value pair in firestore.
//        HashMap<String, String> data = new HashMap<>();
//        data.put("user_posted_question", newQuestion.getUserPostedQuestion().getId());
//        collectionReference
//                .document(newQuestion.getContent())
//                .set(data);
//
//    }
}
