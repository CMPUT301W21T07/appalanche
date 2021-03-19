package com.team007.appalanche.Trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

/**
 * This class represents a measurement-based trial; anything that can be measured,
 * such as temperature or distance, is attributed to this trial-type.
 */

public class MeasurementTrial extends com.team007.appalanche.trial.Trial {
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
