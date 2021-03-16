import java.util.ArrayList;
import java.util.Date;

public class MeasurementTrial extends Trial{
    private double value;
    /*Constructor for the measurement trial class*/
    public MeasurementTrial(Experimenter userAddedTrial, ArrayList<Location> locationList, Date date) {
        super(userAddedTrial, locationList, date);
    }
    /*getter ans setter for the class*/
    public void setValue(double value){
        this.value=value;
    }
    public double getValue (){
        return value;
    }
}