package com.team007.appalanche.qrcode;

import com.team007.appalanche.Experiment.*;

//The QRCode abstract class represents a QR code
public abstract class QRCode {
    private final Experiment experiment;

    // The QRCode class constructor method
    // @param experiment: the experiment whose trial is encoded by the QR code
    public QRCode(Experiment experiment) {
        this.experiment = experiment;
    }
}
