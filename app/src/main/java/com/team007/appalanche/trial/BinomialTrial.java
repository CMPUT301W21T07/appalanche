package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

/**
 * This class represents a binomial trial; values are measured as either true or false.
 */

public class BinomialTrial extends com.team007.appalanche.trial.Trial {
    private boolean outcome;

    /*Constructor function for the binomial trial class*/
    public BinomialTrial(User userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }

    public BinomialTrial(User userAddedTrial, Date date) {
        super(userAddedTrial, date);
    }

    /*GETTER AND SETTER FOR THE CLASS THAT GIVES US THE BOOLEAN OUTCOME OF THE TRIAL*/
    public void setOutcome(boolean outcome){
        this.outcome= outcome;
    }

    public boolean getOutcome(){
        return  outcome;
    }
}