package com.team007.appalanche.Trial;

import com.team007.appalanche.User.Experimenter;
import com.team007.appalanche.Location;

import java.util.ArrayList;
import java.util.Date;

public class BinomialTrial extends Trial{
    private boolean outcome;
    /*Constructor function for the binomial trial class*/
    public BinomialTrial(Experimenter userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }
    /*GETTER AND SETTER FOR THE CLASS THAT GIVES US THE BOOLEAN OUTCOME OF THE TRIAL*/
    public void setOutcome(boolean outcome){
        this.outcome= outcome;
    }
    public boolean getOutcome(){
        return  outcome;
    }
}