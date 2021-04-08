package com.team007.appalanche.experiment;

import java.util.ArrayList;

/**
 * This interface serves as the interface for all experiment-types, and contains
 * the methods that each experiment will use.
 */

public interface ExperimentInterface {
    public void obtainStatistics(); //change to lowercase o?
    public ArrayList<Integer> obtainHistogram();
    public void obtainPlot();
    public void addQuestion();
    public void obtainMap();
}
