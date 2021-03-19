package com.team007.appalanche.scannableCode;

import com.team007.appalanche.Location;
import com.team007.appalanche.Trial.Trial;
import com.team007.appalanche.User.Experimenter;

public interface scannable {
    public Trial scan(Experimenter experimenter, Location location);
    public String serialize();
}
