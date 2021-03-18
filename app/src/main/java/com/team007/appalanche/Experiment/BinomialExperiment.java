package com.team007.appalanche.Experiment;

import com.team007.appalanche.User.User;
import com.team007.appalanche.barcode.Barcode;
import com.team007.appalanche.barcode.BinomialBarcode;
import com.team007.appalanche.qrcode.QRCode;

public class BinomialExperiment extends Experiment implements ExperimentInterface {
    public String trialType;

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
                              String trialType,
                              Integer minNumTrials,
                              Boolean locationRequired,
                              Boolean status,
                              User experimentOwner) {
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
    public BinomialBarcode registerBarcode(int barcode, boolean intendedResult) {
        return new BinomialBarcode(this, barcode, intendedResult);
    }
}
