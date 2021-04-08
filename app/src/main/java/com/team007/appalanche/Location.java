package com.team007.appalanche;

import java.io.Serializable;

/**
 * This class represents an experiment's geolocation;
 * this is done by using latitude and longitude,
 * and a String describing the location.
 */

public class Location implements Serializable {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLat() {
        return latitude;
    }
    public double getLon() {
        return longitude;
    }
}