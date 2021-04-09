package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * This abstract class is the building-block for any Trial; a BinomialTrial,
 * CountBasedTrial, MeasurementTrial, or NonNegativeCountTrial is built from
 * this class.
 */

public class Trial implements Serializable, Comparable<Trial> {
    private Date date;
    private Location location;
    private User userAddedTrial;

    /* constructor function for the trial class*/
    public Trial(User userAddedTrial, Location location, Date date) {
        this.userAddedTrial = userAddedTrial;
        this.location = location;
        this.date = date;
    }

    public Trial(User userAddedTrial, Date date) {
        this.userAddedTrial = userAddedTrial;
        this.date = date;
    }

    // No argument constructor for firebase
    public Trial(){}

    /*getters and setters for the variables in the trial class*/
    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public User getUserAddedTrial() {
        return userAddedTrial;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setUserAddedTrial(User userAddedTrial) {
        this.userAddedTrial = userAddedTrial;
    }

    @Override
    public int compareTo(Trial o) {
        return getDate().compareTo(o.getDate());
    }
}
