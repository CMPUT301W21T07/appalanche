package com.team007.appalanche.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team007.appalanche.R;
import com.team007.appalanche.question.Question;

import java.util.ArrayList;

/**
 * Allows for the proper display of an Question item and its attributes in a ListView display of experiments.
 */

public class QuestionCustomList extends ArrayAdapter<Question> {
    private ArrayList<Question> questions;
    private Context context;
    public QuestionCustomList(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
        this.context = context;
        this.questions = questions;
    }
    @SuppressLint("DefaultLocale")
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_question,parent,false);
        }
        Question question = questions.get(position);

        // Display Question List
        TextView questionContent = view.findViewById(R.id.questionContent);
        questionContent.setText(question.getContent());
        // Display user posted question
        TextView userPostedQues = view.findViewById(R.id.user_posted_question);
        userPostedQues.setText(question.getUserPostedQuestion().getId());
        return view;
    }
}
