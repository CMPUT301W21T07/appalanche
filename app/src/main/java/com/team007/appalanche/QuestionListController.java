package com.team007.appalanche;

import java.util.ArrayList;

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
