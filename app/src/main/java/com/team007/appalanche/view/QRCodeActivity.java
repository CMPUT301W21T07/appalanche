package com.team007.appalanche.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.team007.appalanche.experiment.BinomialExperiment;
import com.team007.appalanche.experiment.CountBasedExperiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.experiment.NonNegativeCountExperiment;
import com.team007.appalanche.R;
import com.team007.appalanche.user.User;
import com.team007.appalanche.scannableCode.BinomialScannableCode;
import com.team007.appalanche.scannableCode.CountBasedScannableCode;
import com.team007.appalanche.scannableCode.MeasurementScannableCode;
import com.team007.appalanche.scannableCode.NonNegScannableCode;

/**
 * This activity is opened when a user wants to create a QR-code.
 * It creates a QR-code for the user to save to their camera roll.
 *
 * Outstanding issues:
 * - User needs to screenshot the QR-code for it to be saved to their camera roll,
 * rather than the app doing it for them.
 */

public class QRCodeActivity extends AppCompatActivity {
    // TESTING PURPOSES ONLY
    // refactor code once we worry about persistent data
    // figure out how to pass experiment information onto this activity
    BinomialExperiment binomialExp = new BinomialExperiment("Can I do a handstand?", "region",
            2, false, false, null);
    CountBasedExperiment countExp = new CountBasedExperiment("How many times have you cried " +
            "listening to driver's license?", "region", 2, false, true, null);
    MeasurementExperiment measurementExp = new MeasurementExperiment("How many litres of water " +
            "did you drink today?", "region", 2, false, true, null);
    NonNegativeCountExperiment nonNegExp = new NonNegativeCountExperiment("How many jelly beans " +
            "cna I fit in my mouth?", "region", 1, false, false, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        ImageView ivOutput;
        Button btnGenerate;

        switch (type) {
            case "binomial":
                setContentView(R.layout.fragment_binomial_qr_layout);
                Button btnPass = findViewById(R.id.btn_pass);
                Button btnFail = findViewById(R.id.btn_fail);
                ivOutput = findViewById(R.id.binomial_qrcode_output);
                btnFail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean intendedBinomial = false;
                        // call the generateQRCode method from BinomialExperiment and pass intendedBinomial as the intended result and save as a BinomialScannableCode
                        BinomialScannableCode binomialQRCode = binomialExp.generateQRcode(intendedBinomial);
                        String toEncode = binomialQRCode.serialize();
                        createQRCode(ivOutput, toEncode);
                    }
                });
                btnPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean intendedBinomial = true;
                        // call the generateQRCode method from BinomialExperiment and pass intendedBinomial as the intended result and save as a BinomialScannableCode
                        BinomialScannableCode binomialQRCode = binomialExp.generateQRcode(intendedBinomial);
                        String toEncode = binomialQRCode.serialize();
                        createQRCode(ivOutput, toEncode);
                    }
                });
                break;
            case "count":
                setContentView(R.layout.fragment_count_qr_layout);
                final EditText inputCount = findViewById(R.id.count_qr_intended_result);
                ivOutput = findViewById(R.id.count_qrcode_output);
                btnGenerate = findViewById(R.id.btn_generate);
                btnGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int intendedCount = Integer.parseInt(inputCount.getText().toString());
                        // call the generateQRCode method from CountBasedExperiment and pass intendedCount as the intended result and save as a CountBasedScannableCode
                        // ask how to included intendedCount???
                        CountBasedScannableCode countQRCode = countExp.generateQRcode();
                        String toEncode = countQRCode.serialize();
                        createQRCode(ivOutput, toEncode);
                    }
                });
                break;
            case "measurement":
                setContentView(R.layout.fragment_measurement_qr_layout);
                final EditText inputMeasurement = findViewById(R.id.measurement_qr_intended_result);
                ivOutput = findViewById(R.id.measurement_qrcode_output);
                btnGenerate = findViewById(R.id.btn_generate);
                btnGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float intendedMeasurement = Float.parseFloat(inputMeasurement.getText().toString());
                        // call the generateQRCode method from MeasurementExperiment and pass intendedMeasurement as the intended result and save as a MeasurementScannableCode
                        MeasurementScannableCode measurementQRCode = measurementExp.generateQRcode(intendedMeasurement);
                        String toEncode = measurementQRCode.serialize();
                        createQRCode(ivOutput, toEncode);
                    }
                });
                break;
            case "nonNegativeCount":
                setContentView(R.layout.fragment_nonneg_qr_layout);
                final EditText inputNonNeg = findViewById(R.id.nonneg_qr_intended_result);
                ivOutput = findViewById(R.id.nonneg_qrcode_output);
                btnGenerate = findViewById(R.id.btn_generate);
                btnGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int intendedNonNeg = Integer.parseInt(inputNonNeg.getText().toString());
                        // call the generateQRCode method from NonNegExperiment and pass intendedNonNeg as the intended result and save as a NonNegScannableCode
                        NonNegScannableCode nonnegQRCode = nonNegExp.generateQRcode(intendedNonNeg);
                        String toEncode = nonnegQRCode.serialize();
                        createQRCode(ivOutput, toEncode);
                    }
                });
                break;
            default:
                //exit
                break;
        }
    }

    public void createQRCode(ImageView iv, String encoded) {
        // tutorial from https://youtu.be/kwOZEU0UBVg
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            // initialize bit matrix
            BitMatrix matrix = writer.encode(encoded, BarcodeFormat.QR_CODE, 500, 500);
            // initialize barcode encoder
            BarcodeEncoder encoder = new BarcodeEncoder();
            // initialize bitmap
            Bitmap bitmap = encoder.createBitmap(matrix);
            // set bitmap on image view
            iv.setImageBitmap(bitmap);

            //save onto device??
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Your QR code has been created! Screenshot it to save.",Toast.LENGTH_SHORT).show();
    }
}

