package com.team007.appalanche.experiment;

import com.jjoe64.graphview.series.DataPoint;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

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

    public ArrayList<User> ignoredUsers = new ArrayList<User>();

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
        trials.add(trial);
    }

    /**
     * To add an ignored user to the list of a specific experiment
     */
    public void addIgnoredUser(User user) {
        ignoredUsers.add(user);
    }

    public ArrayList<User> getIgnoredUsers() {
        return ignoredUsers;
    }

    public DataPoint[] obtainPlot() throws ParseException {
        switch (getTrialType()) {
            case "binomial":
                return getBinomialPlot();
            case "count":
                return getCountPlot();
            case "measurement":
                return getMeasurementPlot();
            case "nonNegativeCount":
                return getNonNegPlot();
            default:
                return new DataPoint[0];
        }
    }

    private DataPoint[] getBinomialPlot() throws ParseException {
        Map<Date, ArrayList<Integer>> map = new TreeMap<Date, ArrayList<Integer>>();

        for(Trial trial: trials) {
            // Get the date without time
            // Taken from stackoverflow.com
            // Post: https://stackoverflow.com/q/5050170
            // User: https://stackoverflow.com/users/193634/rosdi-kasim
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(trial.getDate()));

            ArrayList<Integer> values = map.get(date) != null ?
                    map.get(date): new ArrayList<Integer>();
            int outcome = ((BinomialTrial) trial).getOutcome() ? 1 : 0;
            values.add(outcome);
            map.put(date, values);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        for(Map.Entry<Date, ArrayList<Integer>> val : map.entrySet()) {
            double proportion = 0;
            for(Integer j: val.getValue()) {
                proportion += j;
            }
            proportion = proportion / val.getValue().size();

            points[i] = new DataPoint(val.getKey(), proportion);
            i++;
        }

        return points;
    }

    private DataPoint[] getCountPlot() throws ParseException {
        Map<Date, Integer> map = new TreeMap<Date, Integer>();

        for(Trial trial: trials) {
            // Get the date without time
            // Taken from stackoverflow.com
            // Post: https://stackoverflow.com/q/5050170
            // User: https://stackoverflow.com/users/193634/rosdi-kasim
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(trial.getDate()));

            int value = map.get(date) != null ?
                    map.get(date) + 1 : 1;
            map.put(date, value);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        int count = 0;
        for(Map.Entry<Date, Integer> val : map.entrySet()) {
            count += val.getValue();
            points[i] = new DataPoint(val.getKey(), count);
            i++;
        }

        return points;
    }

    private DataPoint[] getMeasurementPlot() throws ParseException {
        Map<Date, ArrayList<Double>> map = new TreeMap<Date, ArrayList<Double>>();

        for(Trial trial: trials) {
            // Get the date without time
            // Taken from stackoverflow.com
            // Post: https://stackoverflow.com/q/5050170
            // User: https://stackoverflow.com/users/193634/rosdi-kasim
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(trial.getDate()));

            ArrayList<Double> values = map.get(date) != null ?
                    map.get(date): new ArrayList<Double>();
            double result = ((MeasurementTrial) trial).getValue();
            values.add(result);
            map.put(date, values);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        for(Map.Entry<Date, ArrayList<Double>> val : map.entrySet()) {
            double mean = 0;
            for(double j: val.getValue()) {
                mean += j;
            }
            mean = mean / val.getValue().size();

            points[i] = new DataPoint(val.getKey(), mean);
            i++;
        }

        return points;
    }

    private DataPoint[] getNonNegPlot() throws ParseException {
        Map<Date, ArrayList<Integer>> map = new TreeMap<Date, ArrayList<Integer>>();

        for (Trial trial : trials) {
            // Get the date without time
            // Taken from stackoverflow.com
            // Post: https://stackoverflow.com/q/5050170
            // User: https://stackoverflow.com/users/193634/rosdi-kasim
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sdf.format(trial.getDate()));

            ArrayList<Integer> values = map.get(date) != null ?
                    map.get(date) : new ArrayList<Integer>();
            int count = ((NonNegativeCountTrial) trial).getCount();
            values.add(count);
            map.put(date, values);
        }

        DataPoint[] points = new DataPoint[map.keySet().size()];

        int i = 0;
        for (Map.Entry<Date, ArrayList<Integer>> val : map.entrySet()) {
            double mean = 0;
            for (Integer j : val.getValue()) {
                mean += j;
            }
            mean = mean / val.getValue().size();

            points[i] = new DataPoint(val.getKey(), mean);
            i++;
        }

        return points;
    }
}
