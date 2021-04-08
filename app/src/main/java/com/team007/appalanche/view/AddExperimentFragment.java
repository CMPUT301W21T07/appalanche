package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.team007.appalanche.experiment.BinomialExperiment;
import com.team007.appalanche.experiment.CountBasedExperiment;
import com.team007.appalanche.R;

import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.experiment.NonNegativeCountExperiment;
import com.team007.appalanche.user.User;

/**
 * This Fragment is created when a user wants to create an experiment.
 * The user can select attributes such as the minimum number of trials,
 * the region of the experiment, and the experiment's description.
 */
public class AddExperimentFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;

    public static AddExperimentFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("User", user);
        AddExperimentFragment fragment = new AddExperimentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_experiment, null);

        // Get EditTexts
        EditText experimentDescription = view.findViewById(R.id.expDescription);
        EditText experimentRegion = view.findViewById(R.id.expRegion);
        EditText minTrials = view.findViewById(R.id.expTrials);
        Switch geoRequired = view.findViewById(R.id.geoRequired);

        // Initialize Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Initialize Spinner
        String[] types = {"COUNT", "BINOMIAL", "NON NEGATIVE COUNT", "MEASUREMENT"};
        Spinner spin = (Spinner) view.findViewById(R.id.expType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        return builder
                .setView(view)
                .setTitle("NEW EXPERIMENT")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        User user = (User) getArguments().getSerializable("User");

                        // Get the experiment details
                        String description = experimentDescription.getText().toString();
                        String region = experimentRegion.getText().toString();
                        int numTrials;
                        try {
                            numTrials = Integer.valueOf(minTrials.getText().toString());
                        } catch (NumberFormatException e) {
                            numTrials = 0;
                        }

                        // Get the type of the experiment
                        String type = spin.getSelectedItem().toString();

                        // Get whether geolocation is required
                        boolean geolocation = geoRequired.isChecked();

                        switch(type) {
                            case "COUNT":
                                Experiment countExp =
                                        new CountBasedExperiment(description, region,
                                                numTrials, geolocation, true, user.getId());
                                listener.addExperiment(countExp);
                                break;
                            case "BINOMIAL":
                                Experiment binomialExp =
                                        new BinomialExperiment(description, region,
                                                numTrials, geolocation, true, user.getId());
                                listener.addExperiment(binomialExp);
                                break;
                            case "NON NEGATIVE COUNT":
                                Experiment nonNegExp =
                                        new NonNegativeCountExperiment(description, region,
                                                numTrials, geolocation, true, user.getId());
                                listener.addExperiment(nonNegExp);
                                break;
                            case "MEASUREMENT":
                                Experiment measurementExp =
                                        new MeasurementExperiment(description, region,
                                                numTrials, geolocation, true, user.getId());
                                listener.addExperiment(measurementExp);
                                break;
                        }
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
}