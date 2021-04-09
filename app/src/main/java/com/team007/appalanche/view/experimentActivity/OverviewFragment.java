package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static com.team007.appalanche.view.experimentActivity.TrialsFragment.trialListController;

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
        //setUpFirebase();
        Toast.makeText(getContext(), String.valueOf(experiment.getTrials().size()), Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_experiment_overview, container, false);

        TextView description = root.findViewById(R.id.description);
        description.setText(experiment.getDescription());

        TextView owner = root.findViewById(R.id.owner);
        owner.setText("Owner: " + experiment.getExperimentOwnerID());

        // If user click on owner ID, go to the owner profile page
        viewAProfile(owner);

        TextView status = root.findViewById(R.id.status);
        if (experiment.getOpen()) {
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

        // SET UP HISTOGRAM HERE
//        setUpFirebase();
        GraphView histogram = root.findViewById(R.id.histogram);
        histogram.getGridLabelRenderer().setHorizontalAxisTitle("Trial Result");
        histogram.getGridLabelRenderer().setVerticalAxisTitle("Number of Trials");
        // count-based does not have any histogram graph
        if (!experiment.getTrialType().equals("count")) {
            BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint());
            histogram.addSeries(series);}



        return root;
    }

    public void viewAProfile(TextView owner) {
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(),
                        ProfileActivity.class);
                String string = owner.getText().toString();
                String userID = (String) string.subSequence(7, string.length());
                intent.putExtra("Profile", new User(userID));
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private DataPoint[] getDataPoint() {
        int size1 = trialListController.getExperiment().getTrials().size();
        Map<Double, Integer> hm;
        double[] resultList = new double[size1];
        if (experiment.getTrialType().equals("nonNegativeCount")) {
            resultList =  getDataHistogram1(trialListController.getExperiment().getTrials());
            hm = countFrequenciesForDouble(resultList);
        }
        else if (experiment.getTrialType().equals("binomial")) {
            resultList = getDataHistogram3(trialListController.getExperiment().getTrials());
            hm = countFrequenciesForDouble(resultList);
        }
        else {
            resultList = getDataHistogram2(trialListController.getExperiment().getTrials());
            hm = countFrequenciesForDouble(resultList);
        }
        DataPoint[] series = new DataPoint[hm.size()];
        int i = 0;
        for (Map.Entry<Double, Integer> val : hm.entrySet()) {
            series[i] = new DataPoint(val.getKey(), val.getValue());
            i = i +1;
        }
        return series;
    }

    // GET DATA FUNCTION FOR NON-NEGATIVE COUNT TRIALS
    public double[] getDataHistogram1(ArrayList<Trial> trialList) {
        double[] countList = new  double[trialList.size()];

        for (int i = 0; i < trialList.size(); i++) {
            NonNegativeCountTrial trial = (NonNegativeCountTrial) trialList.get(i);
            countList[i] = trial.getCount();
        }

        return countList;
    }

    
    public double[] getDataHistogram2(ArrayList<Trial> trialList) {
        double[] MeasurementList  = new double[trialList.size()];
        for (int i = 0; i < trialList.size(); i++) {
            MeasurementTrial trial = (MeasurementTrial) trialList.get(i);
            MeasurementList[i] =  trial.getValue();
        }
        return MeasurementList;
    }

    public double[] getDataHistogram3(ArrayList<Trial> trialList) {
        double[] binomialList = new double[trialList.size()];
        for (int i = 0; i < trialList.size(); i++) {
            BinomialTrial trial = (BinomialTrial) trialList.get(i);
            if (trial.getOutcome())
                binomialList[i] = 1.0; // ADD 1 FOR TRUE
            else
                binomialList[i] = 2.0;  // ADD 2 FOR FALSE
        }
        return binomialList;
    }

    public  Map<Double, Integer> countFrequenciesForDouble(double[] list)
    {
        Map<Double, Integer> tm = new TreeMap<>();
        for (Double i : list) {
            // Get the current occurence into j -> y-axis
            // The value is in i -> x axis
            Integer j = tm.get(i);
            tm.put(i, (j == null) ? 1 : j + 1);
        }
        return tm;
    }


}