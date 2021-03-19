package com.team007.appalanche.view.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.view.ExperimentActivity;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

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
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setExperimentType(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mainpage_tab_fragment, container, false);

        // Obtain the IDs
        expList = root.findViewById(R.id.expList);

        ExperimentDataList = new ArrayList<>();
        Experiment newExp = new Experiment("How many jelly beans can I fit in my mouth?", "Alberta", "NonNegTrial",2, false, true, null);
        ExperimentDataList.add(newExp);
        // Set up the adapter for Experiment List View
        expAdapter = new CustomList(this.getActivity(), ExperimentDataList);
        expList.setAdapter(expAdapter);

        // Open the experiment
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openExperimentActivity();
            }
        });
        return root;
    }

    public void openExperimentActivity() {
        Intent intent = new Intent(this.getActivity(), ExperimentActivity.class);
        startActivityForResult(intent,1);
    }
}