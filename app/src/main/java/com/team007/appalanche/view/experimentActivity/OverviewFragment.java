package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;

/**
 * A placeholder fragment containing a simple view.
 */
public class OverviewFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Experiment experiment;

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
        Intent intent = getActivity().getIntent();
        experiment = (Experiment) intent.getSerializableExtra("Experiment");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_overview, container, false);

        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());

        TextView owner = root.findViewById(R.id.owner);
        owner.setText("Owner: " + experiment.getExperimentOwnerID());

        TextView status = root.findViewById(R.id.status);
        if (experiment.getStatus()) {
            status.setText("Status: Open");
        } else {
            status.setText("Status: Closed");
        }

        TextView region = root.findViewById(R.id.region);
        region.setText("Region:" + experiment.getRegion());

        TextView currentTrialNum = root.findViewById(R.id.currentNumbTrials);
        currentTrialNum.setText("Current number of trials: " + experiment.getTrials().size());

        TextView minTrialNum = root.findViewById(R.id.minTrials);
        minTrialNum.setText("Minimum number of trials: " + experiment.getMinNumTrials().toString());

        return root;
    }
}