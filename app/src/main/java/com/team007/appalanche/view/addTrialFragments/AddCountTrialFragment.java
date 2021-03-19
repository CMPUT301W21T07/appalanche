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

import java.util.Date;


public class AddCountTrialFragment extends DialogFragment  {
    private OnFragmentInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.count_trial_fragment,null);


        EditText result = view.findViewById(R.id.addCountResult);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        Trial newCountBasedTrial = new CountBasedTrial(new Experimenter("123", null), new Location(), new Date());
                        // newTrial.setOutcome(Boolean.valueOf(result.toString())); TODO: set trial result for trial object
                        listener.addTrial(newCountBasedTrial);
                    }
                })

                .create();
    }

    public interface OnFragmentInteractionListener {
        void addTrial(Trial trial);
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