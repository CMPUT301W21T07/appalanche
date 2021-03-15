package com.team007.appalanche;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private double value;

    public MeasurementTrial(Date date, Experimenter userAddedTrial, Location location, double value) {
        super(date, userAddedTrial, location);
        this.value = value;
    }
}

