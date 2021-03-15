package com.team007.appalanche;

import java.util.Date;

public class NonNegCountTrial extends Trial{
    private int count;

    public NonNegCountTrial(Date date, Experimenter userAddedTrial, Location location, int count) {
        super(date, userAddedTrial, location);
        this.count = count;
    }
}
