package com.team007.appalanche.user;

import com.team007.appalanche.experiment.*;

/**
 * This class represents an owner of an experiment; they have exclusive privileges
 * for the experiments they own: they can unpublish/publish them or end/open them.
 */

public class Owner extends com.team007.appalanche.user.User {
    public Owner(String ID, Profile profile) {
        super(ID, profile);
    }

    public void publishExperiment(Experiment experiment) {
        // TODO: implement function
    }

    public void unPublishExperiment(Experiment experiment) {
        // TODO: implement function
    }

    public void ignoreResults(User experimenter) {
        // TODO: implement function
    }

    public void endExperiment(Experiment experiment) {
        // TODO: implement function
    }
}
