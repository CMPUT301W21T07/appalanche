package com.team007.appalanche.experiment;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

/**
 * This interface serves as the interface for all experiment-types, and contains
 * the methods that each experiment will use.
 */

public interface ExperimentInterface {
    public void obtainStatistics(); //change to lowercase o?
    public ArrayList<Integer> obtainHistogram();
    public DataPoint[] obtainPlot();
    public void addQuestion();
}
