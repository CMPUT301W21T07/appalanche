package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;

/**
 * A placeholder fragment containing a simple view.
 */
public class OverviewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static OverviewFragment newInstance(int index) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the experiment that started the activity
        Intent intent = getActivity().getIntent();
        Experiment experiment = (Experiment) intent.getSerializableExtra("Experiment");

        //DISPLAY DESCRIPTION
//        TextView description = getActivity().findViewById(R.id.description);
//        description.setText(experiment.getDescription());

        
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_overview, container, false);

        return root;
    }
}