package com.team007.appalanche.barcode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

/**
 * This is the measurement barcode class that represents a barcode which encodes a measurement
 * trial.
 */
public class MeasurementBarcode extends Barcode {
    private final double result;

    /**
     * This is the measurement barcode class constructor function.
     * @param experiment
     *  This is the measurement experiment whose trial the barcode encodes
     * @param barcode
     *  This is the barcode number that encodes the measurement trial
     * @param result
     *  This is the double result that the barcode encodes
     */
    public MeasurementBarcode(Experiment experiment, int barcode, double result) {
        super(experiment, barcode);
        this.result = result;
    }

    /**
     * Based on the scanned barcode, this method creates a measurement trial and returns it.
     * @param experimenter The experimenter who scanned the barcode
     * @param location The location that the barcode was scanned at
     * @return Returns the constructed measurement trial
     */
    public MeasurementTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        MeasurementTrial trial = new MeasurementTrial(experimenter, location, date);
        return trial;
    }

    public double getResult() {
        return result;
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