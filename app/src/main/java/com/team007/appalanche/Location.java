public class Location {
    private String latitude;
    private String longitude;
    private String location;


    /* constructor for class location*/
    public void setLocation(String latitude, String longitude, String locationstring) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }
    /*This fuction returns the location of the experiment*/
    public String getLocation(){
    return location;
    }
}
