package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

public class  NonNegativeCountTrial extends Trial{
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
        this.count = count;
    }

}