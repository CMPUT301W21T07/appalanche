package com.team007.appalanche;

import com.team007.appalanche.question.*;
import com.team007.appalanche.user.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    String content = "a question";
    Profile profile = new Profile();
    User user = new User("test", profile);
    Date date = new Date();
    Question question = new Question(content, user, date);

    Reply reply = new Reply("a reply", user, date);

    @Test
    void testGetContent() {
        assertEquals(question.getContent(), content);
    }

    @Test
    void testAddReply() {
        question.addReply(reply);
        assertTrue(question.getReplies().contains(reply));
    }

    @Test
    void testGetReply() {
        Reply reply1 = new Reply("1", user, date);
        Reply reply2 = new Reply("2", user, date);
        Reply reply3 = new Reply("3", user, date);

        question.addReply(reply1);
        question.addReply(reply2);
        question.addReply(reply3);

        assertEquals(question.getReplies().size(), 3);
    }
}
