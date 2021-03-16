import java.util.ArrayList;
import java.util.Date;

public class MeasurementTrial extends Trial{
    private double value;

    public MeasurementTrial(Experimenter userAddedTrial, ArrayList<Location> locationList, Date date) {
        super(userAddedTrial, locationList, date);
    }

    public void setValue(double value){
        this.value=value;
    }
    public double getValue (){
        return value;
    }
}