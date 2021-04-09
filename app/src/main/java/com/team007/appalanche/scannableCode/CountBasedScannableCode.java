package com.team007.appalanche.scannableCode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.experiment.*;
import com.team007.appalanche.user.*;
import com.team007.appalanche.trial.*;

import java.util.Date;

/**
 * This is the count based code class that represents a code which encodes a count based
 * trial.
 */
public class CountBasedScannableCode extends ScannableCode {

    /**
     * This is the count based code class constructor function for the barcode.
     * @param experiment
     *  This is the count based experiment whose trial the code encodes
     * @param barcode
     *  This is the code number encodes the count based trial
     */
    public CountBasedScannableCode(Experiment experiment, String barcode) {
        super(experiment,experiment.getTrialType(), barcode);
    }

    /**
     * This is the count based code class constructor function for the QR code.
     * @param experiment
     *  This is the count based experiment whose trial the code encodes
     */
    public CountBasedScannableCode(Experiment experiment) {
        super(experiment, experiment.getTrialType(), null);
    }

    // No argument constructor for firebase
    public CountBasedScannableCode() {}

    /**
     * Based on the scanned code, this method creates a count-based trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @param location The location that the code was scanned at
     * @return Returns the constructed count-based trial
     */
    public CountBasedTrial scan(User experimenter, Location location) {
        Date date = new Date();
        CountBasedTrial trial = new CountBasedTrial(experimenter, location, date);
        return trial;
    }

    /**
     * Based on the scanned code, this method creates a count-based trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @return Returns the constructed count-based trial
     */
    public CountBasedTrial scan(User experimenter) {
        Date date = new Date();
        CountBasedTrial trial = new CountBasedTrial(experimenter, date);
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
}
