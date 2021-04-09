package com.team007.appalanche.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.ReplyListController;
import com.team007.appalanche.custom.ReplyCustomList;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.question.Reply;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.Date;

/**
 * This activity is opened from QuestionActivity when a user wants to reply to
 * a question. It displays a list whose reply elements are
 * displayed under the printed question. Users can add replies
 * to that list using an EditText or they can return to
 * QuestionActivity using the back button.
 */

public class ReplyActivity extends AppCompatActivity {

    private EditText replyMessage;

    // list display tools
    public ListView replyListView; // public so that can be used in ReplyActivityTest
    private ArrayAdapter<Reply> replyAdapter;
    private ArrayList<Reply> replyDataList;
    private FirebaseFirestore db;
    private ReplyListController replyListController;
    private String TAG = "Sample";
    private Question question;
    private User replyingUser;
    private Experiment experiment;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        // Add back button in action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        replyMessage = findViewById(R.id.reply_message);
        replyListView = findViewById(R.id.reply_list);

        ///////////////////////RECEIVING INTENTS//////////////////////////////////////
        Intent intent = getIntent();
        question = (Question) intent.getSerializableExtra("Question");
        replyingUser = (User) intent.getSerializableExtra("Replying User");
        experiment = (Experiment) intent.getSerializableExtra("Experiment");
        index = intent.getIntExtra("Index", 0);
        //////////////////////////////////////////////////////////////////////////////

        replyListController = new ReplyListController(question);

        replyDataList = replyListController.getQuestion().getReplies();

        setUpFirebase();

        ///////////////////////REPLY LIST BUILDING////////////////////////////////////
        replyAdapter = new ReplyCustomList(this, replyListController.getQuestion().getReplies());
        replyListView = findViewById(R.id.reply_list);
        replyListView.setAdapter(replyAdapter);
        //////////////////////////////////////////////////////////////////////////////


        /////////////////// DISPLAY QUESTION//////////////////////////////////////////
        TextView displayQuestion = findViewById(R.id.display_question);
        String questionString = question.getContent();
        displayQuestion.setText(questionString);
        //////////////////////////////////////////////////////////////////////////////


        /////////////////////////////////Reply Button///////////////////////////////////
        final ImageButton replyButton = findViewById(R.id.reply_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replyMessage != null){
                    String replyString = replyMessage.getText().toString();
                    Reply newReply = new Reply(replyString, replyingUser, new Date());
                    replyMessage.getText().clear();
                    replyListController.addReplyToDb(newReply, experiment);
                    replyAdapter.notifyDataSetChanged();
                }
                //end of if statement
            }
            // end of onClick
        });
        //end of listener
        //////////////////////////////////////////////////////////////////////////////
        replyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reply reply = replyDataList.get(position);
                String userID = reply.getUserReplied().getId();
                Intent intent = new Intent(ReplyActivity.this, ProfileActivity.class);
                intent.putExtra("Profile", new User(userID));
                startActivity(intent);
            }
        });

    }

    // BACK BUTTON
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent backIntent = new Intent();

                // only need to send question (and its index) back since replies are attached to it
                backIntent.putExtra("Question", question);
                backIntent.putExtra("Index", index);

                // go back to QuestionActivity
                setResult(RESULT_OK, backIntent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpFirebase() {
        ///////FIRESTORE////////
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        // Get a top-level reference to the collection.
        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Questions/"+question.getContent()+"/Replies");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                replyListController.clearReplyList();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("user_posted_question")));
                    String content = doc.getId();
                    String user = (String) doc.getData().get("userPostedReply");
                    Date date = (Date) doc.getTimestamp("date").toDate();
                    replyListController.addReply(new Reply(content, new User(user, null), date));}
                replyAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud.
            }
        });
    }
}