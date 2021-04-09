package com.team007.appalanche.experiment;

import com.jjoe64.graphview.series.DataPoint;
import com.team007.appalanche.scannableCode.NonNegScannableCode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class that extends Experiment handles the creation of non-negative count trials.
 */

public class NonNegativeCountExperiment extends Experiment implements ExperimentInterface, Serializable {
    //public String trialType = "NonNegativeCount";
    /**
     * Constructor function for non negative count experiment class
     * @param description
     * @param region
     * @param minNumTrials
     * @param locationRequired
     * @param status
     */
    public NonNegativeCountExperiment(String description,
                                      String region,
                                      Integer minNumTrials,
                                      Boolean locationRequired,
                                      Boolean status,
                                      String experimentOwner) {
        super(description, region, "nonNegativeCount", minNumTrials, locationRequired, status, experimentOwner);
    }

    // No argument constructor for firebase
    public NonNegativeCountExperiment(){}

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
     * function to obtain experiment time plot
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
     * @param intendedResult
     *  The result that I want the registered  to encode
     */
    public NonNegScannableCode registerBarcode(String barcode, int intendedResult) {
        return new NonNegScannableCode(this, intendedResult, barcode);
    }

    /**
     * function to create a barcode
     * @param intendedResult
     *  The result that I want the registered  to encode
     */
    public NonNegScannableCode generateQRcode(int intendedResult) {
        return new NonNegScannableCode(this, intendedResult);
    }
}
