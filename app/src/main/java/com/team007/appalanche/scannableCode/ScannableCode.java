package com.team007.appalanche.scannableCode;

import com.team007.appalanche.experiment.*;

/**
 * This is the ScannableCode abstract class that represents a QR code or a barcode.
 * - if the barcode is -1, then it is a QR code
 */
public abstract class ScannableCode implements scannable {
    private final Experiment experiment;
    private final String barcode;
    private final String experimentType;

    /**
     * This is the barcode class constructor function.
     * @param experiment
     *  This is the experiment whose trial the barcode encodes
     * @param barcode
     *  This is the barcode number encodes the trial
     * @param experimentType
     *  This is the type of experiment
     */
    public ScannableCode(Experiment experiment, String experimentType, String barcode) {
        this.experiment = experiment;
        this.barcode = barcode;
        this.experimentType = experimentType;
    }

    // Getter functions
    public Experiment getExperiment() {
        return experiment;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getExperimentType() {
        return experimentType;
    }

    public boolean isBarcode() {
        return barcode != null;
    }
}
