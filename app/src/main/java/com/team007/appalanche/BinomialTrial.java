import java.util.ArrayList;
import java.util.Date;

public class BinomialTrial extends Trial{
    private boolean outcome;

    public BinomialTrial(Experimenter userAddedTrial, ArrayList<Location> locationList, Date date) {
        super(userAddedTrial, locationList, date);
    }

    public void setOutcome(boolean outcome){
        this.outcome= outcome;
    }
    public boolean getOutcome(){
        return  outcome;
    }
}