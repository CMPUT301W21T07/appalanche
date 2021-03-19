package com.team007.appalanche.user;

import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.trial.*;

/**
 * This class represents an experimenter using the app; experimenters
 * can create experiments and participate in other experiments, but
 * cannot unpublish or end experiments they do not own.
 */

public class Experimenter extends com.team007.appalanche.user.User {

    public Experimenter(String ID, com.team007.appalanche.user.Profile profile) {
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