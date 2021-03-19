package com.team007.appalanche.Experiment;

/**
 * abstract class representing an experiment object
 */
public class Experiment {
    private String description;
    private String region;
    private String trialType;
    private Integer minNumTrials;
    private Boolean locationRequired;
    private Boolean status;
    private String experimentOwnerID;

    /**
     * Constructor function for abstract experiment class
     * @param description
     * @param region
     * @param minNumTrials
     * @param locationRequired
     * @param status
     */
    public Experiment(String description,
                      String region,
                      String trialType,
                      Integer minNumTrials,
                      Boolean locationRequired,
                      Boolean status,
                      String experimentOwnerID) {
        this.description = description;
        this.region = region;
        this.trialType = trialType;
        this.minNumTrials = minNumTrials;
        this.locationRequired = locationRequired;
        this.status = status;
        this.experimentOwnerID = experimentOwnerID;
    }

    // No argument constructor for firebase
    public Experiment() {}

    public Experiment(String description) {this.description = description;}
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
     * boolean location is requied
     */
    public Boolean getLocationRequired() { return this.locationRequired; }

    /**
     * function to change locationRequired
     * @param locationRequired
     *  boolean for location required
     */
    public void setLocationRequired(Boolean locationRequired) { this.locationRequired = locationRequired; }

    /**
     * function to get exp. status
     * @return
     * return true or false
     */
    public Boolean getStatus() { return this.status; }

    /**
     * function to change exp. status
     * @param status
     *  boolean value for experiment status
     */
    public void setStatus(Boolean status) { this.status = status; }

    /**
     * function to get experiment owner
     * @return
     *  return profile object of the individual who created the experiment
     */
    public String getExperimentOwnerID(){ return this.experimentOwnerID; }
}
