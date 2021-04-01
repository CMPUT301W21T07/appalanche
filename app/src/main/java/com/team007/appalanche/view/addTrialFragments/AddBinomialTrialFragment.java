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
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.AddExperimentFragment;

import java.util.Date;


public class AddBinomialTrialFragment extends DialogFragment  {
    private OnFragmentInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_trial_fragment,null);


        // EditText result = view.findViewById(R.id.addBinomialResult);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("DO YOU WANT TO ADD A POSITIVE TRIAL?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        User user = (User) getArguments().getSerializable("user");
                        BinomialTrial newBinomialTrial = new BinomialTrial(user, new Date(), true);
                        listener.addTrial(newBinomialTrial);
                    }
                })

                .create();
    }

    public interface OnFragmentInteractionListener {
        void addTrial(BinomialTrial trial);
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
    public static AddBinomialTrialFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        AddBinomialTrialFragment fragment = new AddBinomialTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
