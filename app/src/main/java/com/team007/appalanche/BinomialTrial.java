package com.team007.appalanche;

import java.util.Date;

public class BinomialTrial extends Trial {
    private boolean outcome;

    public BinomialTrial(Date date, Experimenter userAddedTrial, Location location,
                         boolean outcome) {
        super(date, userAddedTrial, location);
        this.outcome = outcome;
    }
}
