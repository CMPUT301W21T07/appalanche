package com.team007.appalanche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Experiment> {
    private ArrayList<Experiment> experiments;
    private Context context;

    public CustomList(Context context, ArrayList<Experiment> experiments) {
        super(context, 0, experiments);
        this.context = context;
        this.experiments = experiments;
    }

    @SuppressLint("DefaultLocale")
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }
        Experiment exp = experiments.get(position);

        // Date and Description display
        TextView description = view.findViewById(R.id.description);

        description.setText(exp.getDescription());

        return view;
    }
}
