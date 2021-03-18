package com.team007.appalanche.scannableCode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

/**
 * This is the non-negative code class that represents a code which encodes a non-negative
 * trial.
 */

public class NonNegScannableCode extends ScannableCode {
    private final int result;

    /**
     * This is the non-negative code class constructor function for a barcode.
     * @param experiment
     *  This is the experiment whose trial the code encodes
     * @param barcode
     *  This is the code number encodes the trial
     * @param result
     *  This is the integer result that the code encodes
     */
    public NonNegScannableCode(Experiment experiment, int result, int barcode) {
        super(experiment, "NonNegative", barcode);
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
        super(experiment, "NonNegative", -1);
        this.result = result;
    }

    /**
     * Based on the scanned code, this method creates a non-negative trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @param location The location that the code was scanned at
     * @return Returns the constructed non-negative trial
     */
    public NonNegativeCountTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        NonNegativeCountTrial trial = new NonNegativeCountTrial(experimenter, location, date);
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
