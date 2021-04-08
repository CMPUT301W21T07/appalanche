package com.team007.appalanche;

import java.io.Serializable;

/**
 * This class represents an experiment's geolocation;
 * this is done by using latitude and longitude,
 * and a String describing the location.
 */

public class Location implements Serializable {

    private String locationString;
    private double latitude;
    private double longitude;

    public double getLat() {
        return latitude;
    }

    public double getLon() {
        return longitude;
    }

    public void setLat(double latitude) {
        this.latitude = latitude;
    }

    public void setLon(double longitude){
        this.longitude = longitude;
    }

    public void setLocation(String locationstring){
        this.locationString = locationstring;
    }

    public String getLocation(){
        return locationString;
    }
}