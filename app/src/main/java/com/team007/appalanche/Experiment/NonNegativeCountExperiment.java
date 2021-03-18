package com.team007.appalanche.Experiment;

import com.team007.appalanche.User.User;
import com.team007.appalanche.barcode.MeasurementBarcode;
import com.team007.appalanche.barcode.NonNegBarcode;
import com.team007.appalanche.qrcode.QRCode;

public class NonNegativeCountExperiment extends Experiment implements ExperimentInterface {
    public String trialType = "NonNegativeCount";
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
                                      User experimentOwner) {
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
     * function to get experiment QR code
     */
    @Override
    public QRCode generateQRCode() {
        // TODO: implement
        return null;
    }

    /**
     * function to create a Barcode
     * @param barcode
     *  The barcode that I want to register
     * @param intendedResult
     *  The result that I want the registered barcode to encode
     */
    public NonNegBarcode registerBarcode(int barcode, int intendedResult) {
        return new NonNegBarcode(this, barcode, intendedResult);
    }
}
