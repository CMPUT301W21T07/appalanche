package com.team007.appalanche;

import java.util.ArrayList;
import android.view.View;
import android.widget.Button;


public class Owner extends User {

    public void endExperiment(Experiment currentExp, Button addTrialBtn) {
        /*This method should run when the "End Experiment" button is clicked
        * 1. verify that user is the experiment owner; the current user has the experiment in their list of owned experiments
        * 2. set the experiment's status to false
        * 3. hide the add trial button
        * Note: if we go with these parameters, change in UML
        */

        //currentUser is an attribute in the User superclass, if we end up changing attributes and stuff then rip
        if (currentUser.ownedExpList.contains(currentExp)) {
            currentExp.status = false;
            //the add trial button is declared somewhere else where the activity/view is handled
            addTrialBtn.setVisibility(View.GONE);
        }
    }
}
