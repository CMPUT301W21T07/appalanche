import java.util.ArrayList;
public abstract class Trial{
    private Date date;
    private ArrayList<Location> locationList = new ArrayList<Location>();
    private Experimenter userAddedTrial;

    public Trial(Date date, ArrayList<Location> locationList, Experimenter userAddedTrial){
        this.date=date;
        this.userAddedTrial=userAddedTrial;
        this.locationList=locationList;
    }
    public Date getDate(){
        return date;
    }
    public Experimenter getuserAddedTrial(){
        return userAddedTrial;
    }
    public ArrayList<Location> getlocationList(){
        return locationList;
    }
    
}