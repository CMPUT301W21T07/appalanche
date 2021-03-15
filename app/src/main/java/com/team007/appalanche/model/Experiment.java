package com.team007.appalanche.model;

import java.util.ArrayList;

public class Experiment {
    private String description;
    private ArrayList<Question>  questionList = new ArrayList<Question>();

    public Experiment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    /**
     * 
     * This adds a new question to the question list of an Experiment
     * @param question
     *  This is a question to ask
     */
    public void askQuestion (Question question) {
        questionList.add(question);
    }


}
