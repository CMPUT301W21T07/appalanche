package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.team007.appalanche.R;

public class QRCodeFragment extends DialogFragment {
    Intent intent = getActivity().getIntent();
    String type = intent.getStringExtra("type");


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v;

        switch (type) {
            case "binomial":
                v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_binomial_qr_layout,null);
                Button btnPass = v.findViewById(R.id.btn_pass);
                Button btnFail = v.findViewById(R.id.btn_fail);
                btnFail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean intendedBinomial = false;
                    }
                });
                btnPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean intendedBinomial = true;
                    }
                });
                break;

            case "count":
                v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_count_qr_layout,null);
                final EditText inputCount = v.findViewById(R.id.count_qr_intended_result);
                Button btnGenerateCount = v.findViewById(R.id.btn_generate);
                btnGenerateCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int intendedCount = Integer.parseInt(inputCount.getText().toString());
                    }
                });
                break;

            case "measurement":
                v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_measurement_qr_layout,null);
                final EditText inputMeasurement = v.findViewById(R.id.measurement_qr_intended_result);
                Button btnGenerateMeasurement = v.findViewById(R.id.btn_generate);
                btnGenerateMeasurement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double intendedMeasurement = Double.parseDouble(inputMeasurement.getText().toString());
                    }
                });

                break;

            case "nonNegativeCount":
                v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nonneg_qr_layout,null);
                final EditText inputNonNeg = v.findViewById(R.id.nonneg_qr_intended_result);
                Button btnGenerateNonNeg = v.findViewById(R.id.btn_generate);

                btnGenerateNonNeg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int intendedNonNeg = Integer.parseInt(inputNonNeg.getText().toString());

                    }
                });

                break;

            default:
                //exit dialog somehow
                break;
        }

        return builder.create();
    }
}
