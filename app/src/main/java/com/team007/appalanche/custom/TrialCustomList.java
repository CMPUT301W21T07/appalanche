package com.team007.appalanche.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team007.appalanche.R;
import com.team007.appalanche.question.Question;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;

import java.util.ArrayList;

public class TrialCustomList extends ArrayAdapter<Trial> {
    private ArrayList<Trial> trials;
    private Context context;
    public TrialCustomList(Context context, ArrayList<Trial> trials) {
        super(context, 0, trials);
        this.context = context;
        this.trials = trials;
    }
    @SuppressLint("DefaultLocale")
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_trial,parent,false);
        }

        try {
            //FIX THIS
            CountBasedTrial trial = (CountBasedTrial) trials.get(position);
            // Display trial List
            TextView trialCount = view.findViewById(R.id.trialCount);
            trialCount.setText(String.valueOf(trial.getCount()));

        } catch (Exception e0){

            try {
                //FIX THIS
                BinomialTrial trial = (BinomialTrial) trials.get(position);

                // Display trial List
                TextView trialCount = view.findViewById(R.id.trialCount);
                trialCount.setText(String.valueOf(trial.getOutcome()));
            } catch (Exception e1) {
                try {
                    MeasurementTrial trial = (MeasurementTrial) trials.get(position);

                    TextView trialCount = view.findViewById(R.id.trialCount);
                    trialCount.setText(String.valueOf(trial.getValue()));
                } catch (Exception e2) {
                    try {
                        NonNegativeCountTrial trial = (NonNegativeCountTrial) trials.get(position);

                        TextView trialCount = view.findViewById(R.id.trialCount);
                        trialCount.setText(String.valueOf(trial.getCount()));
                    } catch (Exception e3) {
                        System.out.println("Something went wrong.");
                    }
                }
            }
        }

        return view;
    }
}
