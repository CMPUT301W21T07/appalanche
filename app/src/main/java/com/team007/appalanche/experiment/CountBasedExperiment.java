package com.team007.appalanche.experiment;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jjoe64.graphview.series.DataPoint;
import com.team007.appalanche.scannableCode.CountBasedScannableCode;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.Trial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class that extends Experiment handles the creation of count-based trials.
 */
public class CountBasedExperiment extends Experiment implements ExperimentInterface, Serializable {
    /**
     * Constructor function for count based experiment class
     * @param description
     * @param region
     * @param minNumTrials
     * @param locationRequired
     * @param status
     */
    public CountBasedExperiment(String description,
                                String region,
                                Integer minNumTrials,
                                Boolean locationRequired,
                                Boolean status,
                                String experimentOwner) {
        super(description, region, "count", minNumTrials, locationRequired, status, experimentOwner);
    }

    // Nno argument constructor for firebasr
    public CountBasedExperiment(){}

    /**
     * function to obtain experiment statistics
     */
    @Override
    public void obtainStatistics() {
        // TODO: implement
    }

    /**
     * function to obtain experiment histogram
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ArrayList<Integer> obtainHistogram() {
        // TODO: implement
         //Obtain the list of trials
        ArrayList<Trial> trialList = getTrials();
        ArrayList<Integer> countList = new ArrayList<Integer>();
        for (int i = 0; i < trialList.size(); i++) {
            CountBasedTrial trial = (CountBasedTrial) trialList.get(i);
            //countList.add( trial.getCount());
        }
        countList.sort(Comparator.naturalOrder());
        return countList;

    }

    /**
     * function to obtain map of trial locations
     * @return
     */
    @Override
    public DataPoint[] obtainPlot() {
        // TODO: implement
        return new DataPoint[0];
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
     */
    public CountBasedScannableCode registerBarcode(String barcode) {
        return new CountBasedScannableCode(this, barcode);
    }

    /**
     * function to create a barcode
     */
    public CountBasedScannableCode generateQRcode() {
        return new CountBasedScannableCode(this);
    }
}
