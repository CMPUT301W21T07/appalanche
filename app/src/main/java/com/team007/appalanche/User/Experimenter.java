package com.team007.appalanche.User;

import com.team007.appalanche.qrcode.*;
import com.team007.appalanche.barcode.*;
import com.team007.appalanche.Trial.*;

public class Experimenter extends User{

    public Experimenter(String ID, Profile profile) {
        super(ID, profile);
    }

    public void scanQR(QRCode code) {
        // TODO: implement function
    }

    public void registerBarCode(Barcode barCode) {
        // TODO: implement
    }

    public void addGeoLocation(Trial trial) {
        // TODO: Implement
    }
}
