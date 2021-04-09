package com.team007.appalanche.experiment;

import com.jjoe64.graphview.series.DataPoint;
import com.team007.appalanche.scannableCode.BinomialScannableCode;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.Trial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class that extends Experiment handles the creation of binomial trials.
 */

public class BinomialExperiment extends Experiment implements ExperimentInterface, Serializable {
    /**
     * Constructor function for binomial experiment class
     * @param description
     * @param region
     * @param minNumTrials
     * @param locationRequired
     * @param status
     */
    public BinomialExperiment(String description,
                              String region,
                              Integer minNumTrials,
                              Boolean locationRequired,
                              Boolean status,
                              String experimentOwner) {
        super(description, region, "binomial", minNumTrials, locationRequired, status, experimentOwner);
    }

    // No argument constructor for firebase
    public BinomialExperiment(){}

    /**
     * function to obtain experiment statistics
     */
    @Override
    public void obtainStatistics() {
        // TODO: implement
    }

    /**
     * function to obtain experiment histogram
     * @return
     */
    @Override
    public ArrayList<Integer> obtainHistogram() {
        // TODO: implement
        return null;
    }

    /**
     * Function to obtain experiment time plot
     * @return
     */
    public DataPoint[] obtainPlot() {
        Map<Date, ArrayList<Integer>> map = new TreeMap<Date, ArrayList<Integer>>();

        for(Trial trial: getTrials()) {
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

        return points;
    }

    /**
     * function to add questions to experiment
     */
    @Override
    public void addQuestion() {
        // TODO: implement
    }

    /**
     * function to create a barcode
     * @param barcode
     *  The barcode that I want to register
     * @param intendedResult
     *  The result that I want the registered  to encode
     */
    public BinomialScannableCode registerBarcode(String barcode, boolean intendedResult) {
        return new BinomialScannableCode(this, intendedResult, barcode);
    }

    /**
     * function to create a barcode
     * @param intendedResult
     *  The result that I want the registered  to encode
     */
    public BinomialScannableCode generateQRcode(boolean intendedResult) {
        return new BinomialScannableCode(this, intendedResult);
    }
}
