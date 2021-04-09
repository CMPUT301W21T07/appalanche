package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a binomial trial; values are measured as either true or false.
 */

public class BinomialTrial extends com.team007.appalanche.trial.Trial implements Serializable {
    private boolean outcome;

    // Constructor function for the binomial trial class
    public BinomialTrial(User userAddedTrial, Location location, Date date, boolean outcome) {
        super(userAddedTrial, location, date);
        this.outcome = outcome;
    }

    public BinomialTrial(User userAddedTrial, Date date, boolean outcome) {
        super(userAddedTrial, date);
        this.outcome = outcome;
    }

    // No argument constructor for firebase
    public BinomialTrial(){}

    // Getter and setter for outcome
    public void setOutcome(boolean outcome){
        this.outcome= outcome;
    }

    public boolean getOutcome(){
        return  outcome;
    }
}