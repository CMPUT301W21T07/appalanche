package com.team007.appalanche.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team007.appalanche.R;
import com.team007.appalanche.question.Reply;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Allows for the proper display of a Reply item and its attributes in a ListView display of experiments.
 */

public class ReplyCustomList extends ArrayAdapter<Reply> {
    private ArrayList<Reply> replies;
    private Context context;

    public ReplyCustomList(Context context, ArrayList<Reply> replies) {
        super(context, 0, replies);
        this.context = context;
        this.replies = replies;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_question,parent,false);
        }
        Reply reply = replies.get(position);
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        // Display Reply List
        TextView ReplyContent = view.findViewById(R.id.questionContent);
        ReplyContent.setText(reply.getReplyText());

        // Display user ID
        TextView userReplied = view.findViewById(R.id.user_posted_question);
        userReplied.setText(reply.getUserReplied().getId());
        // Display the date
        TextView date = view.findViewById(R.id.date);
        date.setText(simpleDateFormat.format(reply.getDateReplied()));

        userReplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });
        return view;
    }
}

