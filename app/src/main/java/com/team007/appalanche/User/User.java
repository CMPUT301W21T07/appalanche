package com.team007.appalanche.User;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String ID;
    private Profile profile;
    private ArrayList<Object> subscribedExperiments;
    private ArrayList<Object> ownedExperiments;

    /**
     * constructor function for User object
     * @param ID
     * @param profile
     */
    public User(String ID, Profile profile) {
        this.ID = ID;
        this.profile = profile;

        this.subscribedExperiments = new ArrayList<Object>();
        this.ownedExperiments = new ArrayList<Object>();
    }

    /**
     * get user ID
     * @return
     * returns the user ID
     */
    public String getId() { return this.ID; }

    /**
     * set the profile of the user
     * @param profile
     */
    public void setProfile(Profile profile) { this.profile = profile; }

    /**
     * get the profile of the user
     * @return
     *  returns a profile object
     */
    public Profile getProfile() { return this.profile; }


    /**
     * function to subscribe user to an experiment
     * @param experiment
     */
    public void subscribeToExperiment(Object experiment) {
        this.subscribedExperiments.add(experiment);
    }

    /**
     * function to edit the users contact info
     * @param newInfo
     */
    public void editContactInfo(ContactInfo newInfo) {
        // TODO: implement this function
    }

    // TODO: create browseQuestions() function, this may not exist in this class

    // TODO: create replyToQuestion() function, this may not exist in this class

    // TODO: create askQuestion() function, this may not exist in this class
}
