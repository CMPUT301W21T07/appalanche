package com.team007.appalanche.qrcode;

import com.team007.appalanche.Location;
import com.team007.appalanche.Experiment.*;
import com.team007.appalanche.User.*;
import com.team007.appalanche.Trial.*;

import java.util.Date;

//The NonNegQRCode subclass represents a QR code encoding a non-negative count trial
public class NonNegQRCode extends QRCode {
    private final int result;

    //The NonNegQRCode class constructor method
    //@param experiment: the experiment whose trial the QR code encodes
    //@param result: the trial result encoded in the QR code
    public NonNegQRCode(Experiment experiment, int result) {
        super(experiment);
        this.result = result;
    }

    //The scanQRCode method creates and returns a non-negative count trial
    //@param experimenter: the user conducting the trial
    //@param location: the location/coordinates where the QR code is scanned
    public NonNegativeCountTrial scanQRCode(Experimenter experimenter, Location location) {
        Date date = new Date();
        NonNegativeCountTrial trial = new NonNegativeCountTrial(experimenter, location, date);
        return trial;
    }
}