package com.team007.appalanche;

/**
 * This class represents a reply whenever a user wants to add a reply to a specific experiment question
 */
public class Reply {
    private String replyText;
    private User userReplied;
    //maybe add date?

    public Reply(String replyText, User userReplied) {
        this.replyText = replyText;
        this.userReplied = userReplied;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public User getUserReplied() {
        return userReplied;
    }

    public void setUserReplied(User userReplied) {
        this.userReplied = userReplied;
    }
}
