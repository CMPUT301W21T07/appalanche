package com.team007.appalanche.view.addTrialFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.team007.appalanche.Location;
import com.team007.appalanche.R;
import com.team007.appalanche.trial.BinomialTrial;
import com.team007.appalanche.user.User;

import java.util.Date;

public class AddBinomialTrialFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_trial_fragment,null);

        User user = (User) getArguments().getSerializable("user");
        boolean geoRequired = (boolean) getArguments().getBoolean("geoRequired");

        // Only show geolocation items if it's required
        LinearLayout geo = view.findViewById(R.id.geolocation);
        if (!geoRequired) {
            geo.setVisibility(View.GONE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD BINOMIAL RESULT")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BinomialTrial newBinomialTrial;

                        // Get selected result
                        RadioGroup rg = view.findViewById(R.id.radioGroup);
                        final String value =
                                ((RadioButton)view.findViewById(rg.getCheckedRadioButtonId()))
                                        .getText().toString();
                        boolean result = value.equals("Pass");

                        if (geoRequired) {
                            // Get latitude and longitude
                            double latitude;
                            EditText lat = view.findViewById(R.id.latitude);
                            try {
                                latitude =
                                        Double.parseDouble(lat.getText().toString());
                            } catch (NumberFormatException e) {
                                latitude = 0;
                            }

                            double longitude;
                            EditText lon = view.findViewById(R.id.longitude);
                            try {
                                longitude =
                                        Double.parseDouble(lon.getText().toString());
                            } catch (NumberFormatException e) {
                                longitude = 0;
                            }

                            Location location = new Location(latitude, longitude);
                            newBinomialTrial = new BinomialTrial(user, location, new Date(), result);
                        } else {
                            newBinomialTrial = new BinomialTrial(user, new Date(), result);
                        }

                        listener.addTrial(newBinomialTrial);
                    }
                })
                .create();
    }

    public interface OnFragmentInteractionListener {
        void addTrial(BinomialTrial trial);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public static AddBinomialTrialFragment newInstance(User user, boolean geoRequired) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        args.putSerializable("geoRequired", geoRequired);
        AddBinomialTrialFragment fragment = new AddBinomialTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
