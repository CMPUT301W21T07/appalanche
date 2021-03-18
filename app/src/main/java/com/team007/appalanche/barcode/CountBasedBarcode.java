package com.team007.appalanche.barcode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

/**
 * This is the count based barcode class that represents a barcode which encodes a count based
 * trial.
 */
public class CountBasedBarcode extends Barcode {

    /**
     * This is the count based barcode class constructor function.
     * @param experiment
     *  This is the count based experiment whose trial the barcode encodes
     * @param barcode
     *  This is the barcode number encodes the count based trial
     */
    public CountBasedBarcode(Experiment experiment, int barcode) {
        super(experiment, barcode);
    }

    /**
     * Based on the scanned barcode, this method creates a count-based trial and returns it.
     * @param experimenter The experimenter who scanned the barcode
     * @param location The location that the barcode was scanned at
     * @return Returns the constructed count-based trial
     */
    public CountBasedTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        CountBasedTrial trial = new CountBasedTrial(experimenter, location, date);
        return trial;
    }

    /**
     * This serializes the barcode object and returns it as a string
     * @return The serialized barcode object
     */
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
