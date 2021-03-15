package com.team007.appalanche;

import java.util.Date;

public abstract class Trial {
    private Date date;
    private Experimenter userAddedTrial;
    private Location location;

    public Trial(Date date, Experimenter userAddedTrial, Location location) {
        this.date = date;
        this.userAddedTrial = userAddedTrial;
        this.location = location;
    }
}
