package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.team007.appalanche.Experiment.BinomialExperiment;
import com.team007.appalanche.R;
import com.team007.appalanche.User.User;
import com.team007.appalanche.scannableCode.BinomialScannableCode;

import static androidx.core.content.ContextCompat.getSystemService;

// NOT IN USE, but keep until the QR/barcode stuff are 100% ready

public class QRCodeFragment extends DialogFragment {
    Intent intent = getActivity().getIntent();
    String type = intent.getStringExtra("type");

    // TESTING PURPOSES ONLY
    // refactor code once we worry about persistent data
    BinomialExperiment binomialExp = new BinomialExperiment("Can I do a handstand?", "region", "binomial", 5, false, true, new User());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v;

        switch (type) {
            case "binomial":
                v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_binomial_qr_layout,null);
                Button btnPass = v.findViewById(R.id.btn_pass);
                Button btnFail = v.findViewById(R.id.btn_fail);
                ImageView ivOutput = v.findViewById(R.id.binomial_qrcode_output);
                btnFail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean intendedBinomial = false;
                        // call the generateQRCode method from BinomialExperiment and pass intendedBinomial as the intended result and save as a BinomialScannableCode
                        BinomialScannableCode binomialQRCode = binomialExp.generateQRcode(intendedBinomial);
                        String toEncode = binomialQRCode.serialize();

                        // tutorial from https://youtu.be/kwOZEU0UBVg

                        MultiFormatWriter writer = new MultiFormatWriter();
                        try {
                            // initialize bit matrix
                            BitMatrix matrix = writer.encode(toEncode, BarcodeFormat.QR_CODE, 350, 350);
                            // initialize barcode encoder
                            BarcodeEncoder encoder = new BarcodeEncoder();
                            // initialize bitmap
                            Bitmap bitmap = encoder.createBitmap(matrix);
                            // set bitmap on image view
                            ivOutput.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                    }
                });
                btnPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean intendedBinomial = true;
                        // call the generateQRCode method from BinomialExperiment and pass intendedBinomial as the intended result and save as a BinomialScannableCode
                        BinomialScannableCode binomialQRCode = binomialExp.generateQRcode(intendedBinomial);
                        String toEncode = binomialQRCode.serialize();

                        // tutorial from https://youtu.be/kwOZEU0UBVg

                        MultiFormatWriter writer = new MultiFormatWriter();
                        try {
                            // initialize bit matrix
                            BitMatrix matrix = writer.encode(toEncode, BarcodeFormat.QR_CODE, 350, 350);
                            // initialize barcode encoder
                            BarcodeEncoder encoder = new BarcodeEncoder();
                            // initialize bitmap
                            Bitmap bitmap = encoder.createBitmap(matrix);
                            // set bitmap on image view
                            ivOutput.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
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
                        // call the generateQRCode method from CountBasedExperiment and pass intendedCount as the intended result and save as a CountBasedScannableCode
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
                        // call the generateQRCode method from MeasurementExperiment and pass intendedMeasurement as the intended result and save as a MeasurementScannableCode
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
                        // call the generateQRCode method from NonNegExperiment and pass intendedNonNeg as the intended result and save as a NonNegScannableCode


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
