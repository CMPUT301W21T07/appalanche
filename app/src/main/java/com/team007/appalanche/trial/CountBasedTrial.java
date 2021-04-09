package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a count-based trial; values are measured by incremented counts.
 */
public class  CountBasedTrial extends com.team007.appalanche.trial.Trial implements Serializable {

    // Constructor function for the count based trial class
    public CountBasedTrial(User userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }

    public CountBasedTrial(User userAddedTrial, Date date) {
        super(userAddedTrial, date);
    }

    // No argument constructor for firebase
    public CountBasedTrial(){}
}