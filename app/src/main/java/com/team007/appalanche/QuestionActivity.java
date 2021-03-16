package com.team007.appalanche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements AskQuestionFragment.OnFragmentInteractionListener {

    ListView questionList;
    ArrayList<Question> questionDataList;
    Button askQuestionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        askQuestionButton = findViewById(R.id.question);

        askQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AskQuestionFragment().show(getSupportFragmentManager(), "Ask_Question");
            }
        });

        //gonna simplify the design a little bit for now. now, when a question is
        //clicked we can reply to it, instead of there being a small "reply" button
        //on each list item (tricky to program)

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
}