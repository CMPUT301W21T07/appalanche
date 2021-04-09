package com.team007.appalanche.question;

import com.team007.appalanche.user.User;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a reply whenever a user wants to add a reply to a specific experiment question
 */
public class Reply implements Serializable {
    private String replyText;
    private User userReplied;
    private Date dateReplied;

    /**
     * This is a constructor for Reply class
     * @param replyText
     *  This is the text content for the reply
     * @param userReplied
     *  This contains the user posted the reply to question
     */
    public Reply(String replyText, User userReplied, Date dateReplied) {
        this.replyText = replyText;
        this.userReplied = userReplied;
        this.dateReplied = dateReplied;
    }

    /**
     * This is a getter method to get the date that the reply has been added
     * @return
     */
    public Date getDateReplied() {
        return dateReplied;
    }

    /**
     * Returns a user's reply message.
     * @return
     *  replyText
     */
    public String getReplyText() {
        return replyText;
    }

    public User getUserReplied() {
        return userReplied;
    }
}
