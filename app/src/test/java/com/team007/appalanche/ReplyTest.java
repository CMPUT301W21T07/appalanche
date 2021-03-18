package com.team007.appalanche;

import com.team007.appalanche.question.Reply;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReplyTest {

    @Test
    void testGetReplyText(){
        Reply reply = new Reply("Great idea!", null, new Date());

        String expectedReply = "Great idea!";

        assertEquals(0,expectedReply.compareTo(reply.getReplyText()));
    }
}
