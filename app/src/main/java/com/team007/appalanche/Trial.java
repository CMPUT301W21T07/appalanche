import java.util.ArrayList;
import java.util.Date;

public abstract class Trial {
    private Date date;
    private ArrayList<Location> locationList = new ArrayList<Location>();
    private Experimenter userAddedTrial;


    public Trial(Experimenter userAddedTrial, ArrayList<Location> locationList, Date date) {
        this.userAddedTrial = userAddedTrial;
        this.locationList = locationList;
        this.date = date;
    }

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
