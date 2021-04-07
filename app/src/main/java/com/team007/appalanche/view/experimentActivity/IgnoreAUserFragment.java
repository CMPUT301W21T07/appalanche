package com.team007.appalanche.view.experimentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AddExperimentFragment;
import com.team007.appalanche.view.AskQuestionFragment;

import java.util.Date;

public class IgnoreAUserFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ignore_user,null);

        User ignoredUser = (User) getArguments().getSerializable("ignoredUser");
        TextView ignoreMessage = view.findViewById(R.id.ignoreMessage);
        ignoreMessage.setText("Are you sure you would like to ignore all trials from "+ ignoredUser.getId() + " ?");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("IGNORE EXPERIMENTER")
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("IGNORE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        listener.addIgnoredUser(ignoredUser);
                    }
                })
                .create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IgnoreAUserFragment.OnFragmentInteractionListener) {
            listener = (IgnoreAUserFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void addIgnoredUser(User ignoredUser);

    }

    public static IgnoreAUserFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("ignoredUser", user);
        IgnoreAUserFragment fragment = new IgnoreAUserFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
