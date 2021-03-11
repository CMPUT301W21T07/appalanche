package com.team007.appalanche;

/**
 * This class represents a reply whenever a user wants to add a reply to a specific experiment question
 */
public class Reply {
    private String replyText;
    private User userReplied;

    public Reply(String replyText, User userReplied) {
        this.replyText = replyText;
        this.userReplied = userReplied;
    }
}
