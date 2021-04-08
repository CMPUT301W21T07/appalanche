package com.team007.appalanche;

import com.team007.appalanche.experiment.*;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.user.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is composed of unit tests for asserting the proper functionality of experiments.
 */

public class ExperimentTest {
    ContactInfo contact = new ContactInfo(123, "link");
    Profile profile = new Profile("user", contact);
    Experiment exp = new Experiment("description", "Alberta", "binomial", 1, false, true,
            "testId");

    @Test
    void testGetDescription() {
        assertEquals(exp.getDescription(), "description");
    }

    @Test
    void testSetDescription() {
        String newDesc = "new desc";
        exp.setDescription(newDesc);
        assertEquals(exp.getDescription(), newDesc);
    }

    @Test
    void testGetRegion() {
        assertEquals(exp.getRegion(), "Alberta");
    }

    @Test
    void testSetRegion() {
        String newRegion = "BC";
        exp.setRegion(newRegion);
        assertEquals(exp.getRegion(), newRegion);
    }

    @Test
    void testGetTrialType() {
        assertEquals(exp.getTrialType(), "binomial");
    }

//    @Test
//    void testGetMinNumTrials() {
//        assertEquals(exp.getMinNumTrials(), 1);
//    }

//    @Test
//    void setMinNumTrials() {
//        Integer newMin = 100;
//        exp.setMinNumTrials(newMin);
//        assertEquals(exp.getMinNumTrials(), 100);
//    }

    @Test
    void testGetLocationRequired() {
        assertFalse(exp.getLocationRequired());
    }

    @Test
    void testSetLocationRequired() {
        exp.setLocationRequired(true);
        assertTrue(exp.getLocationRequired());
    }

    @Test
    void testGetStatus() {
        assertTrue(exp.getOpen());
    }

    @Test
    void testSetStatus() {
        exp.setOpen(false);
        assertFalse(exp.getOpen());
    }

    @Test
    void testGetOwnerId() {
        assertEquals(exp.getExperimentOwnerID(), "testId");
    }

    @Test
    void testEndExperiment() {
        // The experiment should now allow new trials to be added
        Experiment experiment = new Experiment("description", "Alberta", "binomial", 1, false, true,
            "testId");
        experiment.setOpen(false);
        User user = new User();
        BinomialTrial trial = new BinomialTrial(user, new Date());
        assertThrows(RuntimeException.class, () -> experiment.addTrial(trial));
    }
}
