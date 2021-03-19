package com.team007.appalanche;

import com.team007.appalanche.User.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is composed of unit tests for asserting the proper functionality of the User class and its subclasses.
 */


public class UserTest {
    ContactInfo contact = new ContactInfo(123, "link");
    Profile profile = new Profile("user", contact);
    User user = new User("testId", profile);

    @Test
    void testGetId() {
        assertEquals(user.getID(), "testId");
    }

    @Test
    void testGetProfile() {
        assertEquals(user.getProfile(), profile);
    }
}
