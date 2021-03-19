package com.team007.appalanche;

import com.team007.appalanche.experiment.*;
import com.team007.appalanche.user.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExperimentTest {
    ContactInfo contact = new ContactInfo(123, "link");
    Profile profile = new Profile("user", contact);
    Experiment exp = new Experiment("description", "Alberta", "binomial", 1, false, true, "testId");

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
        assertTrue(exp.getStatus());
    }

    @Test
    void testSetStatus() {
        exp.setStatus(false);
        assertFalse(exp.getStatus());
    }

    @Test
    void testGetOwnerId() {
        assertEquals(exp.getExperimentOwnerID(), "testId");
    }
}
