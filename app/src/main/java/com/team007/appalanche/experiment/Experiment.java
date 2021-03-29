package com.team007.appalanche.experiment;

import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.Trial;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * abstract class representing an experiment object
 */
public class Experiment implements Serializable {
    private String description;
    private String region;
    private String trialType;
    private Integer minNumTrials;
    private Boolean locationRequired;
    private Boolean open;
    private String experimentOwnerID;
    private ArrayList<Trial> trials = new ArrayList<Trial>();
    private ArrayList<Question> questions = new ArrayList<Question>();

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion( Question question) {
        questions.add(question);
    }
    /**
     * Constructor function for abstract experiment class
     * @param description
     * @param region
     * @param minNumTrials
     * @param locationRequired
     * @param open
     */
    public Experiment(String description,
                      String region,
                      String trialType,
                      Integer minNumTrials,
                      Boolean locationRequired,
                      Boolean open,
                      String experimentOwnerID) {
        this.description = description;
        this.region = region;
        this.trialType = trialType;
        this.minNumTrials = minNumTrials;
        this.locationRequired = locationRequired;
        this.open = open;
        this.experimentOwnerID = experimentOwnerID;
    }

    // No argument constructor for firebase
    public Experiment() {}

    public Experiment(String hello) {
    }

    /**
     * function to get exp. description
     * @return
     *  return the experiment description
     */
    public String getDescription() { return this.description; }

    /**
     * function to set exp. description
     * @param description
     *  description for experiment
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * funtion to get exp. region
     * @return
     *  return the region
     */
    public String getRegion() { return this.region; }

    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    }

    /**
     * function to set exp. region
     * @param region
     *  string of the exp. region
     */
    public void setRegion(String region) { this.region = region; }

    /**
     * function to get exp. minNumTrials
     * @return
     *  return the minimum number of trials for the experiment
     */
    public Integer getMinNumTrials() { return this.minNumTrials; }

    /**
     * function to set the minNumTrials
     * @param minNumTrials
     *  integer for minNumTrials
     */
    public void setMinNumTrials(Integer minNumTrials) { this.minNumTrials = minNumTrials; }

    /**
     * function to get the exp. trial type
     * @return
     *  trial type of exp.
     */
    public String getTrialType() { return this.trialType; }

    /**
     * get whether or not the exp trials need a location
     * @return
     * boolean location is required
     */
    public Boolean getLocationRequired() { return this.locationRequired; }

    /**
     * function to change locationRequired
     * @param locationRequired
     *  boolean for location required
     */
    public void setLocationRequired(Boolean locationRequired) { this.locationRequired = locationRequired; }

    /**
     * Function to get experiment status.
     * @return
     * Returns true if the experiment is still open or false otherwise.
     */
    public Boolean getOpen() { return open; }

    /**
     * Function to change experiment status.
     * @param open
     *  True if the experiment is still open, false otherwise.
     */
    public void setOpen(Boolean open) { this.open = open; }

    /**
     * function to get experiment owner
     * @return
     *  return profile object of the individual who created the experiment
     */
    public String getExperimentOwnerID() {
        return this.experimentOwnerID;
    }

    public ArrayList<Trial> getTrials() {
        return trials;
    }

    /**
     * This method adds a trial to the experiment class (only if the experiment if open)
     * @param trial The trial you would like to add to the experiment
     */
    public void addTrial(Trial trial) {
        // You are only able to add a trial if the experiment is open
        if (open) {
            trials.add(trial);
        } else {
            throw new RuntimeException("You cannot add a trial to this experiment as it is ended");
        }
    }
}
