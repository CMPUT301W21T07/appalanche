package com.team007.appalanche.controller;

import com.team007.appalanche.question.Question;
import com.team007.appalanche.question.Reply;

/**
 * This controller stores replies to a chosen question in a chosen experiment.
 */

public class ReplyListController {
    private Question question;

    public ReplyListController(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void addReply(Reply newReply) {
        question.addReply(newReply);

    }
}
