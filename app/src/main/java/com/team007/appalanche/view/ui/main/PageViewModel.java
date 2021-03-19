package com.team007.appalanche.view.ui.main;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.team007.appalanche.Experiment.Experiment;

import java.util.List;

/**
 * This view model maps the type of experiment to display (owned or subscribed) based on the index
 * - 0 maps to owned experiments
 * - 1 maps to subscribed experiments
 */

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> experimentType = new MutableLiveData<>();
    private LiveData<List<Experiment>> ownedExperiments;
    private LiveData<List<Experiment>> subscribedExperiments;

    public void setExperimentType(int index) {
        experimentType.setValue(index);
    }

    public LiveData<List<Experiment>> getExperiments () {
        if (experimentType.getValue() == 0) {
            return ownedExperiments;
        } else {
            return subscribedExperiments;
        }
    }
}