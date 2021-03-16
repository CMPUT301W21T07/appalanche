package com.team007.appalanche.model;

import com.team007.appalanche.model.User;

/**
 * This class represents a reply whenever a user wants to add a reply to a specific experiment question
 */
public class Reply {
    private String replyText;
    private User userReplied;

    /**
     * This is a constructor for Reply class
     * @param replyText
     *  This is the text content for the reply
     * @param userReplied
     *  This contains the user posted the reply to question
     */
    public Reply(String replyText, User userReplied) {
        this.replyText = replyText;
        this.userReplied = userReplied;
    }
}
