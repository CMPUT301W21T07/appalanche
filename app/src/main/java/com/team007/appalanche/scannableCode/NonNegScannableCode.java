package com.team007.appalanche.scannableCode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.experiment.*;
import com.team007.appalanche.user.*;
import com.team007.appalanche.trial.*;

import java.util.Date;

/**
 * This is the non-negative code class that represents a code which encodes a non-negative
 * trial.
 */

public class NonNegScannableCode extends ScannableCode {
    private int result;

    /**
     * This is the non-negative code class constructor function for a barcode.
     * @param experiment
     *  This is the experiment whose trial the code encodes
     * @param barcode
     *  This is the code number encodes the trial
     * @param result
     *  This is the integer result that the code encodes
     */
    public NonNegScannableCode(Experiment experiment, int result, String barcode) {
        super(experiment, experiment.getTrialType(), barcode);
        this.result = result;
    }

    /**
     * This is the non-negative code class constructor function for a QR code.
     * @param experiment
     *  This is the experiment whose trial the code encodes
     * @param result
     *  This is the integer result that the code encodes
     */
    public NonNegScannableCode(Experiment experiment, int result) {
        super(experiment, experiment.getTrialType(), null);
        this.result = result;
    }

    // No argument constructor for firebase
    public NonNegScannableCode(){}

    /**
     * Based on the scanned code, this method creates a non-negative trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @param location The location that the code was scanned at
     * @return Returns the constructed non-negative trial
     */
    public NonNegativeCountTrial scan(User experimenter, Location location) {
        Date date = new Date();
        NonNegativeCountTrial trial = new NonNegativeCountTrial(experimenter, location, date, result);
        return trial;
    }

    /**
     * Based on the scanned code, this method creates a non-negative trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @return Returns the constructed non-negative trial
     */
    public NonNegativeCountTrial scan(User experimenter) {
        Date date = new Date();
        NonNegativeCountTrial trial = new NonNegativeCountTrial(experimenter, date, result);
        return trial;
    }

    /**
     * This serializes the code object and returns it as a string
     * @return The serialized code object
     */
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getResult() {
        return result;
    }
}
