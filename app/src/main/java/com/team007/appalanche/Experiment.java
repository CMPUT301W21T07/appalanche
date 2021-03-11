package com.team007.appalanche;

import java.util.ArrayList;

public class Experiment {
    private String description;
    private ArrayList<Question>  questionList = new ArrayList<Question>();

    /**
     * This adds a new question to the question list of an Experiment
     * @param question
     *  This is a question to ask
     */
    public void askQuestion (Question question) {
        questionList.add(question);
    }

}
