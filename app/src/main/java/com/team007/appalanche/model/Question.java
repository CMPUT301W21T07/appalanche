package com.team007.appalanche.model;

import com.team007.appalanche.model.Reply;
import com.team007.appalanche.model.User;

import java.util.ArrayList;

/**
 * This class represents a question that a user wants to ask about an Experiment
 */
public class Question {
    private String content;
    private User userPostedQuestion;
    private ArrayList<Reply> replies = new ArrayList<Reply>();

    /**
     * This a constructor for class Question
     * @param content
     *  This is the content of question
     * @param userPostedQuestion
     *  This is the user that has posted the question
     */
    public Question(String content, User userPostedQuestion) {
        this.content = content;
        this.userPostedQuestion = userPostedQuestion;
    }

    /**
     * This is a getter method for content attribute, we obtain the question content through this method
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * This is a getter method for userPostedQuestion, we obtain the user's information using this method
     * @return
     */
    public User getUserPostedQuestion() {
        return userPostedQuestion;
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