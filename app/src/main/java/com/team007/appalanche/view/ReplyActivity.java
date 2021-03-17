package com.team007.appalanche.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.R;
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
    private ListView replyListView;
    private ArrayAdapter<Reply> replyAdapter;
    private ArrayList<Reply> replyDataList;
    private FirebaseFirestore db;

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
        Question originalQuestion = (Question) intent.getSerializableExtra("Question");
        User replyingUser = (User) intent.getSerializableExtra("Replying User");

        replyDataList = new ArrayList<>();
        replyAdapter = new ReplyCustomList(this, originalQuestion.getReplies());
        replyListView.setAdapter(replyAdapter);

        TextView displayQuestion = findViewById(R.id.display_question);

        String questionString = originalQuestion.getContent();

        displayQuestion.setText(questionString);

        final Button replyButton = findViewById(R.id.reply_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replyMessage != null){
                    String replyString = replyMessage.getText().toString();
                    Reply newReply = new Reply(replyString, replyingUser, new Date());
                    replyMessage.getText().clear();
                    originalQuestion.addReply(newReply);
                    replyAdapter.notifyDataSetChanged();
                }
            }
        });

        final Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

//    Reply newReply = new Reply("", currentUser); <= use somewhere