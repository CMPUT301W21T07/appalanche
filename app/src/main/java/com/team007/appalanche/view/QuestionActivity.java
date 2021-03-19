package com.team007.appalanche.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import java.util.Date;


public class QuestionActivity extends AppCompatActivity implements AskQuestionFragment.OnFragmentInteractionListener {

    private QuestionListController questionList;
    private ListView questionListView;
    private ArrayAdapter<Question> questionAdapter;
    private FirebaseFirestore db;
    private String TAG = "Sample";

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

            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
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


        // CLICK ON BACK BUTTON
        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                QuestionActivity.this.startActivity(intent);
            }
        });

        // CLICK ON MENU IMAGE
        final ImageView optionButton = findViewById(R.id.optionButton);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FILL IN HERE
            }
        });
        // CLICK ON OVERVIEW BUTTON
        final Button overviewButton = findViewById(R.id.overviewButton);
        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent overviewIntent = new Intent(QuestionActivity.this, ExperimentActivity.class);
               QuestionActivity.this.startActivity(overviewIntent);
            }
        });


//        // CLICK ON TRIALS BUTTON
//        final Button trialButton = findViewById(R.id.trialButton);
//        trialButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent trialIntent = new Intent(QuestionActivity.this, TrialActivity.class);
//                QuestionActivity.this.startActivity(trialIntent);
//            }
//        });
        // WE'RE AT QUESTION LIST PAGE, NO NEED TO SET UP QUESTION BUTTON

        // CLICK BUTTON "ASK QUESTION" TO ASK A NEW QUESTION
        final Button askQuestionButton;
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
    } // end of onCreate()

    @Override
    public void askQuestion(Question question) {
        //questionList.addQuestionToDb(question, db);
        questionList.addQuestion(question);
        questionAdapter.notifyDataSetChanged();
    }
}
