package com.team007.appalanche.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.User.Profile;
import com.team007.appalanche.User.User;
import com.team007.appalanche.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * This view model maps the type of experiment to display (owned or subscribed) based on the index
 * - 0 maps to owned experiments
 * - 1 maps to subscribed experiments
 */

@SuppressWarnings("UnusedAssignment")
public class ExperimentController extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<Integer> experimentType = new MutableLiveData<>();
    private ArrayList<Experiment> ownedExperiments = new ArrayList<Experiment>();
    private ArrayList<Experiment> subscribedExperiments = new ArrayList<Experiment>();
    private String currentUserKey;

    public void setExperimentType(int index) {
        experimentType.setValue(index);
    }

    public ArrayList<Experiment> getExperiments () {
        if (experimentType.getValue() == 0) {
            return ownedExperiments;
        } else {
            return subscribedExperiments;
        }
    }

    public void loadExperiments() {
        // do firebase stuff by loading in the owned and subscribed experiments
        DocumentReference userDoc = db.collection("Users").document(currentUserKey);
        final User[] currentUser = new User[1];

        userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser[0] = documentSnapshot.toObject(User.class);
            }
        });

        if (currentUser[0] != null) {
            ownedExperiments = currentUser[0].getOwnedExperiments();
            subscribedExperiments = currentUser[0].getSubscribedExperiments();
        }
    }

    public void setCurrentUser(String userKey) {
        currentUserKey = userKey;
    }

    public void addExperiment(Experiment experiment) {
        DocumentReference newExperiment = db.collection("Experiments").document();
        newExperiment.set(experiment);
    }
}