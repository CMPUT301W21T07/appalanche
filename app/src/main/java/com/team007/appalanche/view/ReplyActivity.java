package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.controller.ReplyListController;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.custom.ReplyCustomList;
import com.team007.appalanche.model.Question;
import com.team007.appalanche.model.Reply;
import com.team007.appalanche.model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Functionally, this is a list whose
 * reply elements are displayed under the printed question.
 */
public class ReplyActivity extends AppCompatActivity {

    private Button backButton;
    private EditText replyMessage;

    // list display tools
    public ListView replyListView; // public so that can be used in ReplyActivityTest
    private ArrayAdapter<Reply> replyAdapter;
    private ArrayList<Reply> replyDataList;
    private FirebaseFirestore db;
    private ReplyListController replyListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);


       

        // Firestore stuff
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Replies");

        replyMessage = findViewById(R.id.reply_message);
        replyListView = findViewById(R.id.reply_list);

        Intent intent = getIntent();
        Question question = (Question) intent.getSerializableExtra("Question");
        User replyingUser = (User) intent.getSerializableExtra("Replying User");

        replyListController = new ReplyListController(question);


        replyAdapter = new ReplyCustomList(this, replyListController.getQuestion().getReplies());
        replyListView = findViewById(R.id.reply_list);
        replyListView.setAdapter(replyAdapter);


        // DISPLAY QESTION
        TextView displayQuestion = findViewById(R.id.display_question);

        String questionString = question.getContent();

        displayQuestion.setText(questionString);

        final ImageButton replyButton = findViewById(R.id.reply_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replyMessage != null){
                    String replyString = replyMessage.getText().toString();
                    Reply newReply = new Reply(replyString, replyingUser, new Date());
                    replyMessage.getText().clear();
                    replyListController.getQuestion().addReply(newReply);
                    //replyDataList.add(newReply);
                    replyAdapter.notifyDataSetChanged();
                }
            }
        });

        final ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}

//    Reply newReply = new Reply("", currentUser); <= use somewhere