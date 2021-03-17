package com.team007.appalanche.barcode;

import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

/**
 * This is the binomial barcode class that represents a barcode which encodes a binomial
 * trial.
 */

public class BinomialBarcode extends Barcode {
    private final boolean result;

    /**
     * This is the binomial barcode class constructor function.
     * @param experiment
     *  This is the binomial experiment whose trial the barcode encodes
     * @param barcode
     *  This is the barcode number encodes the binomial trial
     * @param result
     *  This is the boolean result that the barcode encodes
     */
    public BinomialBarcode(Experiment experiment, int barcode, boolean result) {
        super(experiment, barcode);
        this.result = result;
    }

    /**
     * Based on the scanned barcode, this method creates a binomial trial and returns it.
     * @param experimenter The experimenter who scanned the barcode
     * @param location The location that the barcode was scanned at
     * @return Returns the constructed binomial trial
     */
    public BinomialTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        BinomialTrial trial = new BinomialTrial(experimenter, location, date);
        return trial;
    }

    public boolean getResult() {
        return result;
    }
}