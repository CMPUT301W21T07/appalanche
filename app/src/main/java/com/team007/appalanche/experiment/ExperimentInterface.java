package com.team007.appalanche.experiment;

/**
 * This interface serves as the interface for all experiment-types, and contains
 * the methods that each experiment will use.
 */

public interface ExperimentInterface {
    public void obtainStatistics(); //change to lowercase o?
    public void obtainHistogram();
    public void obtainPlot();
    public void addQuestion();
    public void obtainMap();
}
