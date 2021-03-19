package com.team007.appalanche.view.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.view.experimentActivity.ExperimentActivity;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ExperimentController experimentController;

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> ExperimentDataList;

    public static MainTabFragment newInstance(int index) {
        MainTabFragment fragment = new MainTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        experimentController = new ViewModelProvider(this).get(ExperimentController.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //String userKey = getResources().getString(R.string.saved_user_key);
        String userKey = sharedPref.getString("com.team007.Appalanche.user_key", null);

        experimentController.setCurrentUser(userKey);
        experimentController.setExperimentType(index);
        experimentController.loadExperiments();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mainpage_tab_fragment, container, false);

        // Obtain the IDs
        expList = root.findViewById(R.id.expList);

        // Load the experiments

//        // FOR DEBUGGING THE NEW TABBED LAYOUT
        ExperimentDataList = new ArrayList<Experiment>();
        Experiment test = new Experiment("How many jelly mans can a jelly bean fit in its mouth",
                "Edmonton", "NonNegative", 4, false, true, null);
        ExperimentDataList.add(test);

//        ExperimentDataList = experimentController.getExperiments();
//        String context = ExperimentDataList.get(0).getDescription();
//        Toast.makeText(getActivity(), context, Toast.LENGTH_LONG).show();

        // Set up the adapter for Experiment List View
        expAdapter = new CustomList(this.getActivity(), ExperimentDataList);
        expList.setAdapter(expAdapter);

        // Open the experiment
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Experiment experiment = ExperimentDataList.get(position);
                openExperimentActivity(experiment);
            }
        });

        return root;
    }

    /**
     * This opens the experiment activity.
     * @param experiment This is the experiment activity to open.
     */
    private void openExperimentActivity(Experiment experiment) {
        Intent intent = new Intent(getContext(), ExperimentActivity.class);
        intent.putExtra("Experiment", experiment);
        startActivity(intent);
        startActivityForResult(intent,1);
    }
}