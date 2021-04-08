package com.team007.appalanche.experiment;

import com.team007.appalanche.scannableCode.BinomialScannableCode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class that extends Experiment handles the creation of binomial trials.
 */

public class BinomialExperiment extends Experiment implements ExperimentInterface, Serializable {
    //public String trialType;

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
    public void obtainPlot() {
        // TODO: implement
    }

    /**
     * function to obtain map of trial locations
     */
    @Override
    public void obtainMap() {
        // TODO: implement
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
