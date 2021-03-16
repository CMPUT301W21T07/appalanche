import java.util.ArrayList;
import java.util.Date;

public class  CountBasedTrial extends Trial{
    private int count;

    public CountBasedTrial(Experimenter userAddedTrial, ArrayList<Location> locationList, Date date) {
        super(userAddedTrial, locationList, date);
    }

    public void Incementcount(){
        this.count= this.count+1;
    }
    public int getCount(){
        return  count;
    }

}