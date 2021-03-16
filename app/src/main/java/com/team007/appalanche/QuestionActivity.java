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
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.ReplyActivity;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.customList.QuestionCustomList;
import com.team007.appalanche.model.Question;
import com.team007.appalanche.model.User;
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

    }

    @Override
    public void askQuestion(Question question) {
        //questionList.addQuestionToDb(question, db);
        questionList.addQuestion(question);
        questionAdapter.notifyDataSetChanged();

    }

    User currentUser = null;

    //reply to a question
        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Question questionToReply = questionDataList.get(position);

            Intent intent = new Intent(QuestionActivity.this, ReplyActivity.class);
            intent.putExtra("Question", questionToReply);
            intent.putExtra("Replying User", currentUser);
            QuestionActivity.this.startActivity(intent);
        }
    });
}