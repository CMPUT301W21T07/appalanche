package com.team007.appalanche;

import java.io.Serializable;

/**
 * This class represents an experiment's geolocation;
 * this is done by using latitude and longitude,
 * and a String describing the location.
 */

public class Location implements Serializable {
    private String latitude;
    private String longitude;
    private String locationString;


    /* constructor for class location*/
    public void setLocation(String latitude, String longitude, String locationstring) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationString = locationstring;
    }
    /*This function returns the location of the experiment*/
    public String getLocation(){
    return locationString;
    }
}
