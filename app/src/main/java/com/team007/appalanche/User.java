package com.team007.appalanche;

import java.util.ArrayList;

public class User {
    private String ID;
    private Profile profile;
    private ArrayList<Experiment> subscribedExpList;


    public void subscribeToExperiment(Experiment exp){
        subscribedExpList.add(exp);
    }

}


