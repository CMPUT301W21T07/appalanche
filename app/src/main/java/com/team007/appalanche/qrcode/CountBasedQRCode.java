package com.team007.appalanche.qrcode;

import com.team007.appalanche.CountBasedTrial;
import com.team007.appalanche.Experiment;
import com.team007.appalanche.Experimenter;
import com.team007.appalanche.Location;

import java.util.Date;

//The CountBasedQRCode subclass represents a QR code encoding a count-based trial
public class CountBasedQRCode extends QRCode {
    private final int result;

    //The CountBasedQRCode class constructor method
    //@param experiment: the experiment whose trial the QR code encodes
    //@param result: the trial result encoded in the QR code
    public CountBasedQRCode(Experiment experiment, int result) {
        super(experiment);
        this.result = result;
    }

    //The scanQRCode method creates and returns a count-based trial
    //@param experimenter: the user conducting the trial
    //@param location: the location/coordinates where the QR code is scanned
    public CountBasedTrial scanQRCode(Experimenter experimenter, Location location) {
        Date date = new Date();
        CountBasedTrial trial = new CountBasedTrial(date, experimenter, location);
        return trial;
    }
}
