package com.team007.appalanche.controller;

import com.team007.appalanche.model.Question;
import com.team007.appalanche.model.Reply;

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
