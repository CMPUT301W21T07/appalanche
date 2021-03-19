package com.team007.appalanche.view.experimentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.view.AskQuestionFragment;
import com.team007.appalanche.view.ReplyActivity;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private FirebaseFirestore db;
    private String TAG = "Sample";

    public static QuestionListController questionList;
    public static ArrayAdapter<Question> questionAdapter;
    public static ListView questionListView;
    private Experiment experiment;

    public static QuestionFragment newInstance(int index) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        experiment = (Experiment) intent.getSerializableExtra("Experiment");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_question, container, false);

        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());

        // A LIST VIEW OF QUESTION LIST
        questionList = new QuestionListController();
        // set adapter to the new QuestionCustomList
        questionAdapter = new QuestionCustomList(this.getContext(),
                questionList.getQuestionList());
        // Obtain id from layout for Question List View
        questionListView = root.findViewById(R.id.questionListView);
        // Set the content for QuestionListView
        questionListView.setAdapter(questionAdapter);

        // CLICK BUTTON "ASK QUESTION" TO ASK A NEW QUESTION
        final Button askQuestionButton;
        askQuestionButton = root.findViewById(R.id.question);
        askQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AskQuestionFragment().show(getFragmentManager(),
                        "Ask_Question");
            }
        });

        User currentUser = MainActivity.currentUser;

        // Reply to a question
        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question questionToReply = questionList.getQuestionList().get(position);

                Intent intent = new Intent(getActivity(), ReplyActivity.class);
                intent.putExtra("Question", questionToReply);
                intent.putExtra("Replying User", currentUser);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }

}