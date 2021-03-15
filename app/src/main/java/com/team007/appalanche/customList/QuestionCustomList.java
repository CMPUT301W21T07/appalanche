package com.team007.appalanche.customList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team007.appalanche.R;
import com.team007.appalanche.model.Question;

import java.util.ArrayList;

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

        return view;
    }
}
