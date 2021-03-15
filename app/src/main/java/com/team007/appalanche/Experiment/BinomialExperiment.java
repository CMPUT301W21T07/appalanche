package com.team007.appalanche.Experiment;

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
                              Boolean status) {
        super(description, region, "binomial", minNumTrials, locationRequired, status);
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
}
