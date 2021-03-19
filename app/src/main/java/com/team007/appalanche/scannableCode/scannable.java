package com.team007.appalanche.scannableCode;

import com.team007.appalanche.Location;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.Experimenter;
import com.team007.appalanche.user.User;

public interface scannable {
    public Trial scan(Experimenter experimenter, Location location);
    public Trial scan(User experimenter);
    public String serialize();
}
