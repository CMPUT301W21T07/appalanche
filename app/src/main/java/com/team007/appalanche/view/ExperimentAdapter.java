package com.team007.appalanche.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;

import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;

import java.util.ArrayList;

public class ExperimentAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Experiment> experiments;
    private static LayoutInflater inflater = null;

    public ExperimentAdapter(Activity context, ArrayList<Experiment> experiments) {
        this.context = context;
        this.experiments = experiments;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return experiments.size();
    }

    @Override
    public Experiment getItem(int position) {
        return experiments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.content_search_result, null): itemView;
        TextView tvDescription = (TextView) itemView.findViewById(R.id.expDescSearchResult);
        TextView tvUserID = (TextView) itemView.findViewById(R.id.userIDSearchResult);
        TextView tvStatus = (TextView) itemView.findViewById(R.id.statusSearchResult);
        Experiment selectedExp = experiments.get(position);
        tvDescription.setText(selectedExp.getDescription());
        tvUserID.setText(selectedExp.getExperimentOwnerID());
        tvStatus.setText(String.valueOf(selectedExp.getStatus()));
        return itemView;
    }
}
