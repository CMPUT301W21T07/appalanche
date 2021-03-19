package com.team007.appalanche.Trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

/**
 * This class represents a non-negative count-based trial. In comparison to CountBasedTrial,
 * it cannot be incremented and is set by a non-negative number of the user's choice.
 */

public class  NonNegativeCountTrial extends com.team007.appalanche.trial.Trial {
    private Integer count;
    /*constructor function for the count based trial class*/
    public NonNegativeCountTrial(User userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }

    public NonNegativeCountTrial(User userAddedTrial, Date date) {
        super(userAddedTrial, date);
    }

    /*This function returns the total number of count at the end*/
    public int getCount(){
        return this.count;
    }

    public void setCount(Integer count) {
        if (count >= 0) {
            this.count = count;
        } else {
            throw new Error("NonNegativeCountTrial cannot set a negative value");
        }
    }

}