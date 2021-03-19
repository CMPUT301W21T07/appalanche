package com.team007.appalanche.scannableCode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

/**
 * This is the binomial code class that represents a barcode which encodes a binomial
 * trial.
 */
public class BinomialScannableCode extends ScannableCode {
    private final boolean result;

    /**
     * This is the binomial code class constructor function for a barcode.
     * @param experiment
     *  This is the binomial experiment whose trial the code encodes
     * @param barcode
     *  This is the barcode number encodes the binomial trial
     * @param result
     *  This is the boolean result that the code encodes
     */
    public BinomialScannableCode(Experiment experiment, boolean result, int barcode) {
        super(experiment, "Binomial", barcode);
        this.result = result;
    }

    /**
     * This is the binomial code class constructor function for a QR code.
     * @param experiment
     *  This is the binomial experiment whose trial the code encodes
     * @param result
     *  This is the boolean result that the code encodes
     */
    public BinomialScannableCode(Experiment experiment, boolean result) {
        super(experiment, "Binomial", -1);
        this.result = result;
    }

    /**
     * Based on the scanned code, this method creates a binomial trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @param location The location that the code was scanned at
     * @return Returns the constructed binomial trial
     */
    public BinomialTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        BinomialTrial trial = new BinomialTrial(experimenter, location, date);
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

    public boolean getResult() {
        return result;
    }
}