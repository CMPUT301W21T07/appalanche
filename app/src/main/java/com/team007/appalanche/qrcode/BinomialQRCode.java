package com.team007.appalanche.qrcode;

import com.team007.appalanche.Location;

import java.util.Date;

//The BinomialQRCode subclass represents a QR code encoding a binomial trial
public class BinomialQRCode extends QRCode {
    private final boolean result;

    //The BinomialQRCode class constructor method
    //@param experiment: the experiment whose trial the QR code encodes
    //@param result: the trial result encoded in the QR code
    public BinomialQRCode(Experiment experiment, boolean result) {
        super(experiment);
        this.result = result;
    }

    //The scanQRCode method creates and returns a binomial trial
    //@param experimenter: the user conducting the trial
    //@param location: the location/coordinates where the QR code is scanned
    public BinomialTrial scanQRCode(Experimenter experimenter, Location location) {
        Date date = new Date();
        BinomialTrial trial = new BinomialTrial(date, experimenter, location, result);
        return trial;
    }
}
