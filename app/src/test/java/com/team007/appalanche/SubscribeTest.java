package com.team007.appalanche;

import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.user.User;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for subscribing to an experiment.
 */

public class SubscribeTest {
    @Test
    public void test(){

        String randomDesc = UUID.randomUUID().toString(); // https://stackoverflow.com/a/41762
        // Creating a mock experiment so that subscription can be tested
        User autoGenUser = new User("123", null);
        Experiment autoGenExp = new MeasurementExperiment(randomDesc, "Albania", 2, false, false, "123");
        ExperimentController experimentController = new ExperimentController(autoGenUser);
        experimentController.addSubExperiment(autoGenExp);

        assertTrue(autoGenUser.getSubscribedExperiments().contains(autoGenExp));
    }
}
