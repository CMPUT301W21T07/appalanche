package com.team007.appalanche.Trial;

import com.team007.appalanche.User.Experimenter;
import com.team007.appalanche.Location;

import java.util.ArrayList;
import java.util.Date;

public abstract class Trial {
    private Date date;
    private Location location = new Location();
    private Experimenter userAddedTrial;
    /* constructor function for the trial class*/

    public Trial(Experimenter userAddedTrial, Location location, Date date) {
        this.userAddedTrial = userAddedTrial;
        this.location = location;
        this.date = date;
    }
    /*getters and setters for the varialbles in the trial class*/
    public Date getDate() {
        return date;
    }

    public Location getLocationList() {
        return location;
    }

    public Experimenter getUserAddedTrial() {
        return userAddedTrial;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocationList(Location location) {
        this.location = location;
    }

    public void setUserAddedTrial(Experimenter userAddedTrial) {
        this.userAddedTrial = userAddedTrial;
    }
}
