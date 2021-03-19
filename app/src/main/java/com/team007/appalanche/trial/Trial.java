package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

public abstract class Trial {
    private Date date;
    private Location location = new Location();
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

    /*getters and setters for the variables in the trial class*/
    public Date getDate() {
        return date;
    }

    public Location getLocationList() {
        return location;
    }

    public User getUserAddedTrial() {
        return userAddedTrial;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocationList(Location location) {
        this.location = location;
    }

    public void setUserAddedTrial(User userAddedTrial) {
        this.userAddedTrial = userAddedTrial;
    }
}
