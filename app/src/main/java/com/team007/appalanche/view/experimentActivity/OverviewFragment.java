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
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
        region.setText("Region: " + experiment.getRegion());

        TextView currentTrialNum = root.findViewById(R.id.currentNumbTrials);
        currentTrialNum.setText("Current number of trials: " + experiment.getTrials().size());

        TextView minTrialNum = root.findViewById(R.id.minTrials);
        minTrialNum.setText("Minimum number of trials: " + experiment.getMinNumTrials().toString());

        // Histogram set-up
        GraphView histogram = root.findViewById(R.id.histogram);
        histogram.getGridLabelRenderer().setHorizontalAxisTitle("Trial Result");
        histogram.getGridLabelRenderer().setVerticalAxisTitle("Number of Trials");

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint());
        histogram.addSeries(series);

        // Time plot set up
        // US US 01.07.01
        // As an owner or experimenter, I want to see plots of the results of trials over time.
        GraphView timePlot = root.findViewById(R.id.plot);
        ArrayList<Trial> trials = experiment.getTrials();

        switch (experiment.getTrialType()) {
            case "binomial":
                getBinomialPlot(timePlot, trials);
                break;
            case "count":
                getCountPlot(timePlot, trials);
                break;
            case "measurement":
                getMeasurementPlot(timePlot, trials);
                break;
            case "nonNegativeCount":
                getNonNegPlot(timePlot, trials);
                break;
            default:
                break;
        }

        return root;
    }

    private void getBinomialPlot(GraphView timePlot, ArrayList<Trial> trials) {
        Map<Date, ArrayList<Integer>> map = new TreeMap<Date, ArrayList<Integer>>();

        for(Trial trial: trials) {
            ArrayList<Integer> values = map.get(trial.getDate()) != null ?
                    map.get(trial.getDate()): new ArrayList<Integer>();
            int outcome = ((BinomialTrial) trial).getOutcome() ? 1 : 0;
            values.add(outcome);
            map.put(trial.getDate(), values);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        for(Map.Entry<Date, ArrayList<Integer>> val : map.entrySet()) {
            int proportion = 0;
            for(Integer j: val.getValue()) {
                proportion += j;
            }
            proportion = proportion / val.getValue().size();

            points[i] = new DataPoint(val.getKey(), proportion);
            i++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);

        // Format the series
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        // Format the time plot
        timePlot.addSeries(series);
        timePlot.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        timePlot.getGridLabelRenderer().setHumanRounding(false);
        timePlot.getGridLabelRenderer().setPadding(60);
        timePlot.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        timePlot.setTitle("Proportion of Success vs Time");
    }

    private void getCountPlot(GraphView timePlot, ArrayList<Trial> trials) {
        Map<Date, Integer> map = new TreeMap<Date, Integer>();

        for(Trial trial: trials) {
            int value = map.get(trial.getDate()) != null ?
                    map.get(trial.getDate()) + 1 : 1;
            map.put(trial.getDate(), value);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        int count = 0;
        for(Map.Entry<Date, Integer> val : map.entrySet()) {
            count += val.getValue();
            points[i] = new DataPoint(val.getKey(), count);
            i++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);

        // Format the series
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        // Format the time plot
        timePlot.addSeries(series);
        timePlot.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        timePlot.getGridLabelRenderer().setHumanRounding(false);
        timePlot.getGridLabelRenderer().setPadding(60);
        timePlot.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        timePlot.setTitle("Count vs Time");
    }

    private void getMeasurementPlot(GraphView timePlot, ArrayList<Trial> trials) {
        Map<Date, ArrayList<Double>> map = new TreeMap<Date, ArrayList<Double>>();

        for(Trial trial: trials) {
            ArrayList<Double> values = map.get(trial.getDate()) != null ?
                    map.get(trial.getDate()): new ArrayList<Double>();
            double result = ((MeasurementTrial) trial).getValue();
            values.add(result);
            map.put(trial.getDate(), values);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        for(Map.Entry<Date, ArrayList<Double>> val : map.entrySet()) {
            int proportion = 0;
            for(double j: val.getValue()) {
                proportion += j;
            }
            proportion = proportion / val.getValue().size();

            points[i] = new DataPoint(val.getKey(), proportion);
            i++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);

        // Format the series
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        // Format the time plot
        timePlot.addSeries(series);
        timePlot.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        timePlot.getGridLabelRenderer().setHumanRounding(false);
        timePlot.getGridLabelRenderer().setPadding(60);
        timePlot.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        timePlot.setTitle("Mean vs Time");
    }

    private void getNonNegPlot(GraphView timePlot, ArrayList<Trial> trials) {
        Map<Date, ArrayList<Integer>> map = new TreeMap<Date, ArrayList<Integer>>();

        for (Trial trial : trials) {
            ArrayList<Integer> values = map.get(trial.getDate()) != null ?
                    map.get(trial.getDate()) : new ArrayList<Integer>();
            int count = ((NonNegativeCountTrial) trial).getCount();
            values.add(count);
            map.put(trial.getDate(), values);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        for (Map.Entry<Date, ArrayList<Integer>> val : map.entrySet()) {
            int mean = 0;
            for (Integer j : val.getValue()) {
                mean += j;
            }
            mean = mean / val.getValue().size();

            points[i] = new DataPoint(val.getKey(), mean);
            i++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);

        // Format the series
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);

        // Format the time plot
        timePlot.addSeries(series);
        timePlot.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        timePlot.getGridLabelRenderer().setHumanRounding(false);
        timePlot.getGridLabelRenderer().setPadding(60);
        timePlot.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        timePlot.setTitle("Mean vs Time");
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
//        ArrayList<Trial> trialList = experiment.getTrials() ;
//        CountBasedTrial x = (CountBasedTrial) trialList.get(0);

        ArrayList<Integer> countList =  getDataHistogram(trialListController.getExperiment().getTrials());
//        ArrayList<Integer> countList =  getDataHistogram(experiment.getTrials());
        Map<Integer, Integer> hm = countFrequencies(countList);
        DataPoint[] series = new DataPoint[getSize(hm)];
        int i = 0;
        for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
            Toast.makeText(getContext(), String.valueOf(val.getKey()), Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), String.valueOf(val.getValue()), Toast.LENGTH_SHORT).show();
            series[i] = new DataPoint(val.getKey(), val.getValue());
            i = i +1;
        }

//        DataPoint[] series = new DataPoint[] {
//                new DataPoint(countList.get(0) , 1),
//                new DataPoint(2 , 4),
//                new DataPoint(countList.get(1), 10),
//                new DataPoint(countList.get(2), 3)
////                new DataPoint(3, 2),
////                new DataPoint(4, 6)
//        };
        return series;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  ArrayList<Integer> getDataHistogram(ArrayList<Trial> trialList) {
        ArrayList<Integer> countList = new ArrayList<Integer>();

        for (int i = 0; i < trialList.size(); i++) {
            //CountBasedTrial trial = (CountBasedTrial) trialList.get(i);
            //countList.add( trial.getCount());
//            if (experiment.getTrialType().equals("count")) {
//                CountBasedTrial trial = (CountBasedTrial) trialList.get(i);
//                countList.add( trial.getCount());
//            } else if (experiment.getTrialType().equals("binomial")) {
//                BinomialTrial trial = (BinomialTrial) trialList.get(i);
//                countList.add( trial.getOutcome());
//            } else if (experiment.getTrialType().equals("nonNegativeCount")) {
//
//            }
//            else {
//
//            }
        }
        countList.sort(Comparator.naturalOrder());
        return countList;
    }

    public  Map<Integer, Integer> countFrequencies(ArrayList<Integer> list)
    {
        Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for (Integer i : list) {
            // Get the current occurence into j
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }
        return hm;
    }

    public int getSize( Map<Integer, Integer> hm) {
        int i =0;
        for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
            i += 1;
        }
        return i;
    }
}