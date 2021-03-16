package com.team007.appalanche.Trial;

import com.team007.appalanche.User.Experimenter;
import com.team007.appalanche.Location;

import java.util.ArrayList;
import java.util.Date;

public abstract class Trial {
    private Date date;
    private ArrayList<Location> locationList = new ArrayList<Location>();
    private Experimenter userAddedTrial;
    /* constructor function for the trial class*/

    public Trial(Experimenter userAddedTrial, ArrayList<Location> locationList, Date date) {
        this.userAddedTrial = userAddedTrial;
        this.locationList = locationList;
        this.date = date;
    }
    /*getters and setters for the varialbles in the trial class*/
    public Date getDate() {
        return date;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    public Experimenter getUserAddedTrial() {
        return userAddedTrial;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    public void setUserAddedTrial(Experimenter userAddedTrial) {
        this.userAddedTrial = userAddedTrial;
    }
}
