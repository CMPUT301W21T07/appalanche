package com.team007.appalanche.Experiment;

import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.scannableCode.MeasurementScannableCode;

/**
 * This class that extends Experiment handles the creation of measurement-based trials.
 */

public class MeasurementExperiment extends Experiment implements com.team007.appalanche.experiment.ExperimentInterface {
    /**
     * Constructor function for measurement experiment class
     * @param description
     * @param region
     * @param minNumTrials
     * @param locationRequired
     * @param status
     */
    public MeasurementExperiment(String description,
                                 String region,
                                 Integer minNumTrials,
                                 Boolean locationRequired,
                                 Boolean status,
                                 String experimentOwner) {
        super(description, region, "measurement", minNumTrials, locationRequired, status, experimentOwner);
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
    public MeasurementScannableCode registerBarcode(String barcode, float intendedResult) {
        return new MeasurementScannableCode(this, intendedResult, barcode);
    }

    /**
     * function to create a barcode
     * @param intendedResult
     *  The result that I want the registered  to encode
     */
    public MeasurementScannableCode generateQRcode(float intendedResult) {
        return new MeasurementScannableCode(this, intendedResult);
    }

}
