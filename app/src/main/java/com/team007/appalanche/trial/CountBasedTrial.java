package com.team007.appalanche.trial;

import com.team007.appalanche.Location;
import com.team007.appalanche.user.User;

import java.util.Date;

public class  CountBasedTrial extends Trial{
    private int count;

    /*constructor function for the count based trial class*/
    public CountBasedTrial(User userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }

    public CountBasedTrial(User userAddedTrial, Date date) {
        super(userAddedTrial, date);
    }

    /*This function increments the count by 1 whenever called*/
    public void IncrementCount(){
        this.count= this.count+1;
    }

    /*This function returns the total number of count at the end*/
    public int getCount(){
        return  count;
    }

}