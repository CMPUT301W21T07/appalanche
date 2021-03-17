package com.team007.appalanche.Trial;

import com.team007.appalanche.User.Experimenter;
import com.team007.appalanche.Location;

import java.util.ArrayList;
import java.util.Date;

public class MeasurementTrial extends Trial{
    private double value;
    /*Constructor for the measurement trial class*/
    public MeasurementTrial(Experimenter userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }
    /*getter ans setter for the class*/
    public void setValue(double value){
        this.value=value;
    }
    public double getValue (){
        return value;
    }
}
