package com.team007.appalanche.view.addTrialFragments;

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

import com.team007.appalanche.Location;
import com.team007.appalanche.R;
import com.team007.appalanche.trial.*;
import com.team007.appalanche.user.Experimenter;
import com.team007.appalanche.user.User;

import java.util.Date;


public class AddNonNegTrialFragment extends DialogFragment  {
    private OnFragmentInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.non_neg_trial_fragment,null);


        EditText result = view.findViewById(R.id.addNonNegResult);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        NonNegativeCountTrial newNonNegTrial = new NonNegativeCountTrial(new User(), new Date(), Integer.valueOf(result.getText().toString()));
                        // newTrial.setOutcome(Boolean.valueOf(result.toString())); TODO: set trial result for trial object
                        listener.addTrial(newNonNegTrial);
                    }
                })

                .create();
    }

    public interface OnFragmentInteractionListener {
        void addTrial(NonNegativeCountTrial trial);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}