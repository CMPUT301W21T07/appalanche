package com.team007.appalanche.barcode;

import com.team007.appalanche.Experiment;

/**
 * This is the barcode abstract class that represents a barcode.
 */
public abstract class Barcode {
    private final Experiment experiment;
    private final int barcode;

    /**
     * This is the barcode class constructor function.
     * @param experiment
     *  This is the experiment whose trial the barcode encodes
     * @param barcode
     *  This is the barcode number encodes the trial
     */
    public Barcode(Experiment experiment, int barcode) {
        this.experiment = experiment;
        this.barcode = barcode;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public int getBarcode() {
        return barcode;
    }
}
