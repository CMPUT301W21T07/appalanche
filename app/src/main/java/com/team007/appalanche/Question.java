package com.team007.appalanche;

import java.util.ArrayList;

/**
 * This class represents a question that a user wants to ask about an Experiment
 */
public class Question {
    private String content;
    private User userPostedQuestion;
    private ArrayList<Reply> replies = new ArrayList<Reply>();

    public Question(String content, User userPostedQuestion) {
        this.content = content;
        this.userPostedQuestion = userPostedQuestion;
    }

    /**
     * This adds a reply to the reply list of a question
     * @param reply
     *  This is a reply to add to the list
     */
    public void addReply(Reply reply) {
        replies.add(reply);
    }
}