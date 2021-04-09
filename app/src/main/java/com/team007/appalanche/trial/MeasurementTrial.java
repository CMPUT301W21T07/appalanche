package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a measurement-based trial; anything that can be measured,
 * such as temperature or distance, is attributed to this trial-type.
 */

public class MeasurementTrial extends Trial implements Serializable {
    private double value;

    // Constructor for the measurement trial class
    public MeasurementTrial(User userAddedTrial, Location location, Date date, double value) {
        super(userAddedTrial, location, date);
        this.value = value;
    }

    public MeasurementTrial(User userAddedTrial, Date date, double value) {
        super(userAddedTrial, date);
        this.value = value;
    }

    // No argument constructor for firebase
    public MeasurementTrial(){}

    // Getter and setter for value
    public void setValue(double value){
        this.value=value;
    }

    public double getValue (){
        return value;
    }
}
