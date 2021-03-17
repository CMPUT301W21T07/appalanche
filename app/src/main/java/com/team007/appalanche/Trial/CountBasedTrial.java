package com.team007.appalanche.Trial;

import com.team007.appalanche.User.Experimenter;
import com.team007.appalanche.Location;

import java.util.ArrayList;
import java.util.Date;

public class  CountBasedTrial extends Trial{
    private int count;
    /*constructor function for the count based trial class*/
    public CountBasedTrial(Experimenter userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
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