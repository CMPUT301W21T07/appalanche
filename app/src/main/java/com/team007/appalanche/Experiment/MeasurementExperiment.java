package com.team007.appalanche.Experiment;

import com.team007.appalanche.Trial.MeasurementTrial;
import com.team007.appalanche.User.User;
import com.team007.appalanche.qrcode.MeasurementQRCode;

public class MeasurementExperiment extends Experiment implements ExperimentInterface {
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
                                 User experimentOwner) {
        super(description, region, "measurement", minNumTrials, locationRequired, status, experimentOwner);
    }

    /**
     * function to obtain experiment statistics
     */
    @Override
    public void ObtainStatistics() {
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
    public void obtainQR() {
        // TODO: implement
    }

    //generateQRCode()
    public MeasurementQRCode generateQRCode(double intendedResult) {
        return new MeasurementQRCode(this, intendedResult);
    }
}
