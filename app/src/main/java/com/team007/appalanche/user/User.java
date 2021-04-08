package com.team007.appalanche.user;

import com.team007.appalanche.experiment.Experiment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a user using the app. A user can be an Experimenter or an
 * Owner.
 */

public class User implements Serializable {
    private String ID;
    private Profile profile;
    private String userName;
    private ArrayList<Experiment> subscribedExperiments = new ArrayList<Experiment>();
    private ArrayList<Experiment> ownedExperiments=  new ArrayList<Experiment>();

    public User() {}

    /**
     * constructor function for User object
     * @param ID
     * @param profile
     */
    public User(String ID, Profile profile) {
        this.ID = ID;
        this.profile = profile;
        this.subscribedExperiments = new ArrayList<Experiment>();
        this.ownedExperiments = new ArrayList<Experiment>();
    }

    public User(String ID) {
        this.ID = ID;
    }

    /**
     * get user ID
     * @return
     * returns the user ID
     */
    public String getId() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

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
    public void subscribeToExperiment(Experiment experiment) {
        this.subscribedExperiments.add(experiment);
    }

    /**
     * function to edit the users contact info
     * @param newInfo
     */
    public void editContactInfo(ContactInfo newInfo) {
        // TODO: implement this function
    }

    public ArrayList<Experiment> getSubscribedExperiments() {
        return subscribedExperiments;
    }

    public ArrayList<Experiment> getOwnedExperiments() {
        return ownedExperiments;
    }
    // TODO: create browseQuestions() function, this may not exist in this class

    // TODO: create replyToQuestion() function, this may not exist in this class

    // TODO: create askQuestion() function, this may not exist in this class

    public void addOwnedExperiment(Experiment experiment) {
        ownedExperiments.add(experiment);
    }
    public void addSubscribedExperiment(Experiment experiment) {subscribedExperiments.add(experiment);}

}
