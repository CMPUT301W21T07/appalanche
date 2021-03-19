package com.team007.appalanche.scannableCode;

import com.google.gson.Gson;
import com.team007.appalanche.Location;
import com.team007.appalanche.experiment.*;
import com.team007.appalanche.user.*;
import com.team007.appalanche.trial.*;

import java.util.Date;

/**
 * This is the measurement code class that represents a code which encodes a measurement
 * trial.
 */

public class MeasurementScannableCode extends ScannableCode {
    private final double result;

    /**
     * This is the measurement code class constructor function for a barcode.
     * @param experiment
     *  This is the measurement experiment whose trial the code encodes
     * @param barcode
     *  This is the code number that encodes the measurement trial
     * @param result
     *  This is the double result that the code encodes
     */
    public MeasurementScannableCode(Experiment experiment, double result, int barcode) {
        super(experiment, "Measurement", barcode);
        this.result = result;
    }

    /**
     * This is the measurement code class constructor function for the QR code.
     * @param experiment
     *  This is the measurement experiment whose trial the code encodes
     * @param result
     *  This is the double result that the code encodes
     */
    public MeasurementScannableCode(Experiment experiment, double result) {
        super(experiment, "Measurement", -1);
        this.result = result;
    }

    /**
     * Based on the scanned code, this method creates a measurement trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @param location The location that the code was scanned at
     * @return Returns the constructed measurement trial
     */
    public MeasurementTrial scan(Experimenter experimenter, Location location) {
        Date date = new Date();
        MeasurementTrial trial = new MeasurementTrial(experimenter, location, date);
        trial.setValue(result);
        return trial;
    }

    /**
     * Based on the scanned code, this method creates a measurement trial and returns it.
     * @param experimenter The experimenter who scanned the code
     * @return Returns the constructed measurement trial
     */
    public MeasurementTrial scan(User experimenter) {
        Date date = new Date();
        MeasurementTrial trial = new MeasurementTrial(experimenter, date);
        trial.setValue(result);
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

    public double getResult() {
        return result;
    }
}