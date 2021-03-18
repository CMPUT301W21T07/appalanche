package com.team007.appalanche.view;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.User.User;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.R;

import com.team007.appalanche.controller.QuestionListController;

import java.util.Date;


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


        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        // Get a top-level reference to the collection.
        final CollectionReference collectionReference = db.collection("Questions");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,@Nullable FirebaseFirestoreException e) {
                // clear the old list
                questionList.clearQuestionList();
//                if (e != null){
//                    //Toast.makeText(QuestionActivity.this, " deleted", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("user_posted_question")));
                    String question = doc.getId();
                    String user = (String) doc.getData().get("user_posted_question");
                    questionList.addQuestion(new Question(question, new User(user, null), new Date()));}

                //}
                questionAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud.
            }
        });




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
                intent.putExtra("Index", position);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void askQuestion(Question question) {
        //questionList.addQuestionToDb(question, db);
        questionList.addQuestion(question);
        questionAdapter.notifyDataSetChanged();

    }

    // AFTER REPLYING
    // Coding in Flow. "Send Data Back from Child Activity with startActivityForResult - Android Studio Tutorial." YouTube, 16 Jan. 2018, https://youtu.be/AD5qt7xoUU8 [YouTube Standard License]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Question question = (Question) data.getSerializableExtra("Question");
                int index = data.getIntExtra("Index", 0);
                questionList.getQuestionList().set(index,question);
                questionAdapter.notifyDataSetChanged();

            }
        }
    }
}
