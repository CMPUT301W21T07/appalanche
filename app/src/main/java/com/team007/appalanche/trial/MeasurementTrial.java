package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

public class MeasurementTrial extends Trial{
    private double value;
    /*Constructor for the measurement trial class*/
    public MeasurementTrial(User userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }

    public MeasurementTrial(User userAddedTrial, Date date) {
        super(userAddedTrial, date);
    }

    /*getter ans setter for the class*/
    public void setValue(double value){
        this.value=value;
    }
    public double getValue (){
        return value;
    }
}
