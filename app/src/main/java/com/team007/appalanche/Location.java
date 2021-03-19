package com.team007.appalanche;

public class Location {
    private String latitude;
    private String longitude;
    private String locationString;


    /* constructor for class location*/
    public void setLocation(String latitude, String longitude, String locationstring) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationString = locationstring;
    }
    /*This fuction returns the location of the experiment*/
    public String getLocation(){
    return locationString;
    }
}
