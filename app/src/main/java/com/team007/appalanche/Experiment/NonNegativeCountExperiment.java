package com.team007.appalanche.Experiment;

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
                                      Profile experimentOwner) {
        super(description, region, "nonNegativeCount", minNumTrials, locationRequired, status, experimentOwner);
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
