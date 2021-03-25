package com.team007.appalanche.experiment;

import com.team007.appalanche.scannableCode.CountBasedScannableCode;

import java.io.Serializable;

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
