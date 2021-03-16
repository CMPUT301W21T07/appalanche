package com.team007.appalanche;

import com.team007.appalanche.model.Question;
import com.team007.appalanche.model.User;

import org.junit.Test;

/*
 * Test for this class
 * testAddReply
 * getContent
 * getUserPostedQuestion
 */
public class QuestionTest {
    // A method to create mock question object
    private Question mockQuestion() {
        Question question = new Question("How many jelly beans can I fit in my mouth? ", new User("tom"));
        return question;
    }

    @Test
    void testAddReply() {
        //
    }

    @Test
    void testReplyException() {

    }

    @Test
    void testGetContent() {

    }

    @Test
    void testGetUser() {

    }

}
