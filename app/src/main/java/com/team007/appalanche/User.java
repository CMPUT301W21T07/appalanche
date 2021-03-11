package com.team007.appalanche;

import java.util.ArrayList;

public class User {
    private String ID;
    private Profile profile;
    private ArrayList<Experiment> subscribedExpList;

    public void subscribeToExperiment(Experiment exp){
        if (!(subscribedExpList.contains(exp))){
            subscribedExpList.add(exp);
        }
        // idea for UI: print an error message that says "already added"
        // or...remove the subscribe button from an experiment that's
        // being subscribed to...but that could be tricky to make
    }

}


