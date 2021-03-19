package com.team007.appalanche;

import com.team007.appalanche.user.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is composed of unit tests for asserting the proper functionality of profiles.
 */


public class ProfileTest {
    ContactInfo contactInfo = new ContactInfo(123, "link");
    Profile profile = new Profile("username", contactInfo);

    @Test
    void testGetUserName() {
        assertEquals(profile.getUserName(), "username");
    }

    @Test
    void testGetProfile() {
        assertEquals(profile.getContactInfo(), contactInfo);
    }

    @Test
    void testSetUserName() {
        String newUserName = "ricky bobby";
        profile.setUserName(newUserName);
        assertEquals(profile.getUserName(), newUserName);
    }

    @Test
    void testSetContactInfo() {
        ContactInfo newContactInfo = new ContactInfo(456, "new link");
        profile.setContactInfo(newContactInfo);
        assertEquals(profile.getContactInfo(), newContactInfo);
    }
}
