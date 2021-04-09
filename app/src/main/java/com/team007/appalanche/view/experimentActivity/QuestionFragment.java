package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.controller.QuestionListController;
import com.team007.appalanche.custom.QuestionCustomList;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.view.AskQuestionFragment;
import com.team007.appalanche.view.ReplyActivity;
import com.team007.appalanche.view.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private FirebaseFirestore db;
    private String TAG = "Sample";

    public static QuestionListController questionList;
    public static ArrayList<Question> questionDataList;
    public static ArrayAdapter<Question> questionAdapter;
    public static ListView questionListView;
    private Experiment experiment;
    private User user;

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
        user = (User) intent.getSerializableExtra("User");

        questionList = new QuestionListController(experiment);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        setUpFireBase();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_question, container, false);

        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());

        // A LIST VIEW OF QUESTION LIST
        questionDataList = questionList.getExperiment().getQuestions();
        // set adapter to the new QuestionCustomList
        questionAdapter = new QuestionCustomList(this.getContext(), questionDataList);
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
                new AskQuestionFragment().newInstance(user).show(getFragmentManager(),
                        "Ask_Question");
            }
        });


        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();
                if (viewId == R.id.seeReplies) {
                    Question questionToReply = questionDataList.get(position);
                    Intent intent = new Intent(getActivity(), ReplyActivity.class);
                    intent.putExtra("Experiment", experiment);
                    intent.putExtra("Question", questionToReply);
                    intent.putExtra("Replying User", user);
                    startActivity(intent);
                }
                else if (viewId == R.id.user_posted_question) {
                    Question question = questionDataList.get(position);
                    String userID = question.getUserPostedQuestion().getId();
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("Profile", new User(userID));
                    startActivity(intent);
                    // Again fix the back button
                }
            }
        });

        return root;
    }

    public void setUpFireBase() {
        // Get a top-level reference to the collection.
        final CollectionReference collectionReference = db.collection("Experiments/" + experiment.getDescription()+"/Questions");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // clear the old list
                questionList.clearQuestionList();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    Log.d(TAG, String.valueOf(doc.getData().get("user_posted_question")));
                    String content = doc.getId();
                    String user = (String) doc.getData().get("user_posted_question");
                    Date date = (Date) doc.getTimestamp("date").toDate();
                    questionList.addQuestion(new Question(content, new User(user, null), date));}
                questionAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud.
            }
        });
    }
}