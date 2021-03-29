package com.team007.appalanche;

import com.team007.appalanche.controller.ExperimentController;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.view.ui.mainActivity.OwnedFragment;
import com.team007.appalanche.view.ui.mainActivity.SubscribedFragment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubscribeTest {
    @Test
    public void test(){

        // create mock exp
        User autoGenUser = new User("123", null);
        Experiment autoGenExp = new MeasurementExperiment("1", "Albania", 2, false, false, "123");
        ExperimentController experimentController = new ExperimentController(autoGenUser);
        experimentController.addSubExperiment(autoGenExp);

        SubscribedFragment subscribedFragment = new SubscribedFragment();
        assertTrue(subscribedFragment.ExperimentDataList.contains(autoGenExp));


    }
}
