package com.team007.appalanche.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.team007.appalanche.R;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.trial.CountBasedTrial;
import com.team007.appalanche.trial.MeasurementTrial;
import com.team007.appalanche.trial.NonNegativeCountTrial;
import com.team007.appalanche.trial.Trial;
import com.team007.appalanche.view.addTrialFragments.AddBinomialTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddCountTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddMeasurementTrialFragment;
import com.team007.appalanche.view.addTrialFragments.AddNonNegTrialFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TrialCustomList extends ArrayAdapter<Trial> {
    private ArrayList<Trial> trials;
    private Context context;
    private String trialType;

    public TrialCustomList(Context context, ArrayList<Trial> trials, String trialType) {
        super(context, 0, trials);
        this.context = context;
        this.trials = trials;
        this.trialType = trialType;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_trial,parent,false);
        }

        // Get TextView IDs
        TextView trialCount = view.findViewById(R.id.trialCount);
        TextView userAddedTrial = view.findViewById(R.id.userID);
        TextView date = view.findViewById(R.id.date);

        // Create date format pattern
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        switch(trialType) {
            case "binomial":
                BinomialTrial binomialTrial = (BinomialTrial) trials.get(position);

                trialCount.setText(binomialTrial.getOutcome() ? "Pass" : "Fail");
                userAddedTrial.setText(binomialTrial.getUserAddedTrial().getId());
                date.setText(simpleDateFormat.format(binomialTrial.getDate()));
                break;
            case "count":
                CountBasedTrial countTrial = (CountBasedTrial) trials.get(position);

                trialCount.setText("+1");
                userAddedTrial.setText(countTrial.getUserAddedTrial().getId());
                date.setText(simpleDateFormat.format(countTrial.getDate()));
                break;
            case "measurement":
                MeasurementTrial measurementTrial = (MeasurementTrial) trials.get(position);

                trialCount.setText(String.valueOf(measurementTrial.getValue()));
                userAddedTrial.setText(measurementTrial.getUserAddedTrial().getId());
                date.setText(simpleDateFormat.format(measurementTrial.getDate()));
                break;
            case "nonNegativeCount":
                NonNegativeCountTrial trial = (NonNegativeCountTrial) trials.get(position);

                trialCount.setText(String.valueOf(trial.getCount()));
                userAddedTrial.setText(trial.getUserAddedTrial().getId());
                date.setText(simpleDateFormat.format(trial.getDate()));
            default:
                System.out.println("Something went wrong.");
                break;
        }

        // CLICK ON USER ID -> SHOW PROFILE
        userAddedTrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });

        // CLICK ON WARNING IMAGE VIEW
        ImageView ignore =  view.findViewById(R.id.ignoreUser);
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 1); // Let the event be handled in onItemClick()
            }
        });

        return view;
    }

}
