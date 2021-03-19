package com.team007.appalanche;

import com.team007.appalanche.trial.*;
import com.team007.appalanche.user.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BinomialTrialTest {
    Date date = new Date();
    Location location = new Location();
    Profile profile = new Profile();
    User user = new User("test", profile);

    BinomialTrial trial = new BinomialTrial(user, location, date);

    @Test
    void testGetDate() {
        assertEquals(trial.getDate(), date);
    }

    @Test
    void testGetLocation() {
        assertEquals(trial.getLocationList(), location);
    }

    @Test
    void testGetUser() {
        assertEquals(trial.getUserAddedTrial(), user);
    }

    @Test
    void testSetDate() {
        Date newDate = new Date();
        trial.setDate(newDate);
        assertEquals(trial.getDate(), newDate);
    }

    @Test
    void testSetLocation() {
        Location newLocation = new Location();
        trial.setLocationList(newLocation);
        assertEquals(trial.getLocationList(), newLocation);
    }

    @Test
    void testSetUser() {
        User newUser = new User("newUser", profile);
        trial.setUserAddedTrial(newUser);
        assertEquals(trial.getUserAddedTrial(), newUser);
    }

    @Test
    void testGetOutcome() {
        assertEquals(trial.getOutcome(), false);
    }

    @Test
    void testSetOutcome() {
        trial.setOutcome(true);
        assertEquals(trial.getOutcome(), true);
    }
}

class CountBasedTrialTest {
    Date date = new Date();
    Location location = new Location();
    Profile profile = new Profile();
    User user = new User("test", profile);

    CountBasedTrial trial = new CountBasedTrial(user, location, date);

    @Test
    void testGetCount() {
        assertEquals(trial.getCount(), 0);
    }

    @Test
    void testIncrementCount() {
        trial.IncrementCount();
        assertEquals(trial.getCount(), 1);
    }
}

class MeasurementTrialTest {
    Date date = new Date();
    Location location = new Location();
    Profile profile = new Profile();
    User user = new User("test", profile);

    MeasurementTrial trial = new MeasurementTrial(user, location, date);

    @Test
    void testGetValue() {
        assertEquals(trial.getValue(), 0.0);
    }

    @Test
    void testSetValue() {
        trial.setValue(1000);
        assertEquals(trial.getValue(), 1000);
    }
}

class NonNegativeCountTrialTest {
    Date date = new Date();
    Location location = new Location();
    Profile profile = new Profile();
    User user = new User("test", profile);

    NonNegativeCountTrial trial = new NonNegativeCountTrial(user, location, date);

    @Test
    void testGetCount() {
        trial.setCount(1);
        assertEquals(trial.getCount(), 1);
    }

    @Test
    void testSetPositiveCount() {
        trial.setCount(15);
        assertEquals(trial.getCount(), 15);
    }

//    @Test
//    void testSetNegativeCount() {
//        trial.setCount(-15);
//        assertThrows(Error.class, "NonNegativeCountTrial cannot set a negative value");
//    }
}
