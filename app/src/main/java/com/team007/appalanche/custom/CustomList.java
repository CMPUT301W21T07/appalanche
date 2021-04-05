package com.team007.appalanche.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.R;

import java.util.ArrayList;

/**
 * Allows for the proper display of an Experiment item and its attributes in a ListView display of experiments.
 */

public class CustomList extends ArrayAdapter<Experiment> {
    private ArrayList<Experiment> experiments;
    private Context context;

    public CustomList(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.context = context;
        this.experiments = experiments;
    }

    public void update(ArrayList<Experiment> results){
        experiments = new ArrayList<>();
        experiments.addAll(results);
        notifyDataSetChanged();
    }

    @SuppressLint("DefaultLocale")
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_search_result,parent,false);
        }
        Experiment exp;
//        Experiment exp = experiments.get(position);

        try{
            exp = experiments.get(position);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            position--;
            exp = experiments.get(position);
            // exp = experiments.get(0); <-- sometimes a fix but doesn't make much sense why lol
        }


        // Date and Description display
        TextView description = view.findViewById(R.id.expDescSearchResult);
        TextView userID = view.findViewById(R.id.userIDSearchResult);
        TextView status = view.findViewById(R.id.statusSearchResult);

        description.setText(exp.getDescription());
        userID.setText(exp.getExperimentOwnerID());
        if (exp.getStatus()) {
            status.setText("Open");
        } else {
            status.setText("Closed");
        }
        //description.setText(String.valueOf(experiments.size()));
        return view;
    }
}
