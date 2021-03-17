package com.team007.appalanche.qrcode;

import com.team007.appalanche.Location;

import java.util.Date;

//The MeasurementQRCode subclass represents a QR code encoding a measurement-based trial
public class MeasurementQRCode extends QRCode {
    private final double result;

    //The MeasurementQRCode class constructor method
    //@param experiment: the experiment whose trial the QR code encodes
    //@param result: the trial result encoded in the QR code
    public MeasurementQRCode(Experiment experiment, double result) {
        super(experiment);
        this.result = result;
    }

    //The scanQRCode method creates and returns a measurement-based trial
    //@param experimenter: the user conducting the trial
    //@param location: the location/coordinates where the QR code is scanned
    public MeasurementTrial scanQRCode(Experimenter experimenter, Location location) {
        Date date = new Date();
        MeasurementTrial trial = new MeasurementTrial(date, experimenter, location, result);
        return trial;
    }
}
