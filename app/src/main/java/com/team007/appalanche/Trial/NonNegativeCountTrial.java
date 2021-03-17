package com.team007.appalanche.Trial;

import com.team007.appalanche.User.Experimenter;
import com.team007.appalanche.Location;
import java.util.Date;

public class  NonNegativeCountTrial extends Trial{
    private Integer count;
    /*constructor function for the count based trial class*/
    public NonNegativeCountTrial(Experimenter userAddedTrial, Location location, Date date) {
        super(userAddedTrial, location, date);
    }

    /*This function returns the total number of count at the end*/
    public int getCount(){
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}