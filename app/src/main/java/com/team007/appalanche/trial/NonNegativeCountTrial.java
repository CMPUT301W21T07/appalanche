package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a non-negative count-based trial. In comparison to CountBasedTrial,
 * it cannot be incremented and is set by a non-negative number of the user's choice.
 */

public class  NonNegativeCountTrial extends Trial implements Serializable {
    private int count;

    // Constructor function for the count based trial class
    public NonNegativeCountTrial(User userAddedTrial, Location location, Date date, int count) {
        super(userAddedTrial, location, date);
        if (count >= 0) {
            this.count = count;
        } else {
            throw new Error("NonNegativeCountTrial cannot set a negative value");
        }
    }

    public NonNegativeCountTrial(User userAddedTrial, Date date, int count) {
        super(userAddedTrial, date);
        if (count >= 0) {
            this.count = count;
        } else {
            throw new Error("NonNegativeCountTrial cannot set a negative value");
        }
    }

    // No argument constructor for firebase
    public NonNegativeCountTrial(){}

    // This function returns the total number of count at the end
    public int getCount(){
        return this.count;
    }

    public void setCount(int count) {
        if (count >= 0) {
            this.count = count;
        } else {
            throw new Error("NonNegativeCountTrial cannot set a negative value");
        }
    }
}