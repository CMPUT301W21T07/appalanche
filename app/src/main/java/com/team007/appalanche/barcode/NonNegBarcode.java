package com.team007.appalanche.barcode;

import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

/**
 * This is the non-negative barcode class that represents a barcode which encodes a non-negative
 * trial.
 */
public class NonNegBarcode extends Barcode {
    private final int result;

    /**
     * This is the non-negative barcode class constructor function.
     * @param experiment
     *  This is the experiment whose trial the barcode encodes
     * @param barcode
     *  This is the barcode number encodes the trial
     * @param result
     *  This is the integer result that the barcode encodes
     */
    public NonNegBarcode(Experiment experiment, int barcode, int result) {
        super(experiment, barcode);
        this.result = result;
    }

    /**
     * Based on the scanned barcode, this method creates a non-negative trial and returns it.
     * @param experimenter The experimenter who scanned the barcode
     * @param location The location that the barcode was scanned at
     * @return Returns the constructed non-negative trial
     */
    public NonNegativeCountTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        NonNegativeCountTrial trial = new NonNegativeCountTrial(experimenter, location, date);
        return trial;
    }

    public int getResult() {
        return result;
    }
}
