package com.team007.appalanche.Experiment;

import com.team007.appalanche.scannableCode.NonNegScannableCode;

/**
 * This class that extends Experiment handles the creation of non-negative count trials.
 */

public class NonNegativeCountExperiment extends Experiment implements com.team007.appalanche.experiment.ExperimentInterface {
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
    @Override
    public void obtainHistogram() {
        // TODO: implement
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
    public NonNegScannableCode registerBarcode(int barcode, int intendedResult) {
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
