package com.team007.appalanche.user;

import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.trial.Trial;

public class Experimenter extends User{

    public Experimenter(String ID, Profile profile) {
        super(ID, profile);
    }

    public void scan(ScannableCode code) {
        // TODO: implement function
    }

    public void registerBarCode(ScannableCode barCode) {
        // TODO: implement
    }

    public void addGeoLocation(Trial trial) {
        // TODO: Implement
    }
}