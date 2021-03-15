package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.customList.QuestionCustomList;
import com.team007.appalanche.model.Question;
import com.team007.appalanche.view.AskQuestionFragment;


public class QuestionActivity extends AppCompatActivity implements AskQuestionFragment.OnFragmentInteractionListener {

    Button askQuestionButton;
    QuestionListController questionList;
    ListView questionListView;
    ArrayAdapter<Question> questionAdapter;
    FirebaseFirestore db;
    String TAG = "Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        // A LIST VIEW OF QUESTION LIST
        questionList = new QuestionListController();
        // set adapter to the new QuestionCustomList
        questionAdapter = new QuestionCustomList(this, questionList.getQuestionList());
        // Obtain id from layout for Question List View
        questionListView = findViewById(R.id.questionListView);
        // Set the content for QuestionListView
        questionListView.setAdapter(questionAdapter);


//        // Access a Cloud Firestore instance from your Activity
//        db = FirebaseFirestore.getInstance();
//        // Get a top-level reference to the collection.
//        final CollectionReference collectionReference = db.collection("questions");
//

//        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                // clear the old list
//                questionList.clearQuestionList();
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
//                    if (queryDocumentSnapshots == null){
//                        Toast.makeText(QuestionActivity.this, " deleted", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else {
//                    Log.d(TAG, String.valueOf(doc.getData().get("user_posted_question")));
//                    String question = doc.getId();
//                    String user = (String) doc.getData().get("user_posted_question");
//                    questionList.addQuestion(new Question(question, new User(user)));} // Adding the cities and provinces from FireStore.
//
//                }
//                questionAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud.
//            }
//        });






        // CLICK BUTTON ASK QUESTION TO ASK A NEW QUESTION
        askQuestionButton = findViewById(R.id.question);
        askQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AskQuestionFragment().show(getSupportFragmentManager(), "Ask_Question");
            }
        });

    }

    @Override
    public void askQuestion(Question question) {
        //questionList.addQuestionToDb(question, db);
        questionList.addQuestion(question);
        questionAdapter.notifyDataSetChanged();

    }
}