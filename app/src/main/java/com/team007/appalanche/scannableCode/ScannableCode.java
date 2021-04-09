package com.team007.appalanche.scannableCode;

import com.google.firebase.firestore.Exclude;
import com.team007.appalanche.Location;
import com.team007.appalanche.experiment.*;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;

import java.util.Date;

/**
 * This is the ScannableCode abstract class that represents a QR code or a barcode.
 * - if the barcode is -1, then it is a QR code
 */
public class ScannableCode {
    private Experiment experiment;
    private String barcode;
    private String experimentType;

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

    // No argument constructor for firebase
    public ScannableCode(){}

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

    @Exclude
    public boolean isBarcode() {
        return barcode != null;
    }
}
