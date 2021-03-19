package com.team007.appalanche.view.experimentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.team007.appalanche.R;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddMeasurementTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddNonNegTrialFragment;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrialsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView trialListView;
    private ArrayAdapter<Trial> trialAdapter;
    private ArrayList<Trial> trialDataList;
    private String experimentType = "binomial"; //TODO: hook this up to the actual experiment type, not hard coded value

    public static TrialsFragment newInstance(int index) {
        TrialsFragment fragment = new TrialsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_trials, container, false);

        final Button addTrialButton = root.findViewById(R.id.addTrialButton);
        addTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTrialActivity();
            }
        });
        return root;
    }

    public void openAddTrialActivity() {
        switch(experimentType) {
            case "binomial":
                new AddBinomialTrialFragment().show(getFragmentManager(), "Add_Trial");
                break;
            case "count":
                new AddCountTrialFragment().show(getFragmentManager(), "Add_Trial");
                break;
            case "Measurement":
                new AddMeasurementTrialFragment().show(getFragmentManager(), "Add_trial");
                break;
            case "nonNegative":
                new AddNonNegTrialFragment().show(getFragmentManager(), "Add_Trial");
                break;
        }
        // TODO: hook fragment result to update experiment and create trial
    }

}