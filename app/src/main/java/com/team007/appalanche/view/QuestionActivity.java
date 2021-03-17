package com.team007.appalanche.view;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.model.Question;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.R;

import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.model.User;


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
//        final CollectionReference collectionReference = db.collection("Questions");
//
//        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,@Nullable FirebaseFirestoreException e) {
//                // clear the old list
//                questionList.clearQuestionList();
//                if (e != null){
//                    //Toast.makeText(QuestionActivity.this, " deleted", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
//                    Log.d(TAG, String.valueOf(doc.getData().get("user_posted_question")));
//                    String question = doc.getId();
//                    String user = (String) doc.getData().get("user_posted_question");
//                    questionList.addQuestion(new Question(question, new User(user)));}
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

        User currentUser = null;

        //reply to a question
        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question questionToReply = questionList.getQuestionList().get(position);

                Intent intent = new Intent(QuestionActivity.this, ReplyActivity.class);
                intent.putExtra("Question", questionToReply);
                intent.putExtra("Replying User", currentUser);
                QuestionActivity.this.startActivity(intent);
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

