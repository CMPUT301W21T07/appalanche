package com.team007.appalanche.question;

import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a question that a user wants to ask about an Experiment
 */
public class Question implements Serializable {
    private String content;
    private User userPostedQuestion;
    private Date dateAsked;
    private ArrayList<Reply> replies = new ArrayList<Reply>();

    /**
     * This a constructor for class Question
     * @param content
     *  This is the content of question
     * @param userPostedQuestion
     *  This is the user that has posted the question
     */
    public Question(String content, User userPostedQuestion, Date dateAsked) {
        this.content = content;
        this.userPostedQuestion = userPostedQuestion;
        this.dateAsked = dateAsked;
    }

    /**
     * This is a getter method for content attribute, we obtain the question content through this method
     * @return
     *  content
     */
    public String getContent() {
        return content;
    }

    /**
     * This is a getter for the date when the question has been asked
     * @return
     */
    public Date getDateAsked() {
        return dateAsked;
    }

    /**
     * This adds a reply to the reply list of a question
     * @param reply
     *  This is a reply to add to the list
     */
    public void addReply(Reply reply) {
        replies.add(reply);
    }


    /**
     * Returns all replies belonging to a Question instance.
     * @return
     *  replies
     */
    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public User getUserPostedQuestion() {
        return userPostedQuestion;
    }
}