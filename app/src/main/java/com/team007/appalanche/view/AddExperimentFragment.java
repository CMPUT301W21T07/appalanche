package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team007.appalanche.experiment.BinomialExperiment;
import com.team007.appalanche.experiment.CountBasedExperiment;
import com.team007.appalanche.R;

import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.experiment.NonNegativeCountExperiment;
import com.team007.appalanche.user.Experimenter;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;
import com.team007.appalanche.question.Question;

import java.util.Date;

/**
 * This Fragment is created when a user wants to create an experiment.
 * The user can select attributes such as the minimum number of trials,
 * the region of the experiment, and the experiment's description.
 */


public class AddExperimentFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    private int k;
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_experiment, null);
        EditText experimentDescription = view.findViewById(R.id.textView2);
        EditText experimentregion = view.findViewById(R.id.textView3);
        EditText numberoftrials = view.findViewById(R.id.textView4);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String[] users = {"COUNT", "BINOMIAL", "NON NEGATIVE COUNT", "MEASUREMENT"};
        Spinner spin = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        String typee= spin.getSelectedItem().toString();


        return builder
                .setView(view)
                .setTitle("NEW EXPERIMENT")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        User user = (User) getArguments().getSerializable("user");
                        if(k==1){
                            Experiment newExperiment = new CountBasedExperiment(experimentDescription.getText().toString(), experimentregion.getText().toString(), Integer.valueOf(numberoftrials.getText().toString()), Boolean.FALSE, Boolean.FALSE, user.getId());
                            listener.addExperiment(newExperiment);
                        }
                        else if(k==2){
                            Experiment newExperiment = new BinomialExperiment(experimentDescription.getText().toString(), experimentregion.getText().toString(), Integer.valueOf(numberoftrials.getText().toString()), Boolean.FALSE, Boolean.FALSE, user.getId());
                            listener.addExperiment(newExperiment);
                        } else if (k == 3) {
                            Experiment newExperiment = new NonNegativeCountExperiment(experimentDescription.getText().toString(), experimentregion.getText().toString(), Integer.valueOf(numberoftrials.getText().toString()), Boolean.FALSE, Boolean.FALSE, user.getId());
                            listener.addExperiment(newExperiment);
                        }
                        else if (k==4){
                            Experiment newExperiment = new MeasurementExperiment(experimentDescription.getText().toString(), experimentregion.getText().toString(), Integer.valueOf(numberoftrials.getText().toString()), Boolean.FALSE, Boolean.FALSE, user.getId());
                            listener.addExperiment(newExperiment);
                        }
                        //Todo later(Theo can write this) : Once we have implemented searching for experiments, we should
                        // automatically add owned experiments to the subscribed list;
                        // listener.addSubExp(newExperiment);
                    }
                })

                .create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_experiment, container, false);
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

    public interface OnFragmentInteractionListener {
        void addExperiment(Experiment newExp);
    }
    public static AddExperimentFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        AddExperimentFragment fragment = new AddExperimentFragment();
        fragment.setArguments(args);
        return fragment;
    }
}