package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.team007.appalanche.R;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.user.User;

import java.util.Date;

/**
 * This fragment is created when a user wants to ask a question about
 * an experiment. It contains a text field where the user can enter their question,
 * and it also contains two buttons: Cancel and Post.
 */


public class AskQuestionFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ask_question,null);

        EditText askQuestion = view.findViewById(R.id.askQuestion);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("NEW QUESTION")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        User user = (User) getArguments().getSerializable("user");
                        Question newQuestion = new Question(askQuestion.getText().toString(), user, new Date());
                        listener.askQuestion(newQuestion);
                    }
                })
                .create();
    }

    public interface OnFragmentInteractionListener {
        void askQuestion(Question question);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public static AskQuestionFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        AskQuestionFragment fragment = new AskQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

}