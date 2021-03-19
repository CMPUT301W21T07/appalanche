package com.team007.appalanche.scannableCode;

import com.team007.appalanche.Location;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.user.User;

/**
 * This interface serves as the interface for all scannableCode types, and contains
 * the methods that each scannableCode will use.
 */

public interface scannable {
    public Trial scan(User experimenter, Location location);
    public Trial scan(User experimenter);
    public String serialize();
}
