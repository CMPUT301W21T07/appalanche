package com.team007.appalanche.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.BinomialExperiment;
import com.team007.appalanche.experiment.CountBasedExperiment;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.experiment.MeasurementExperiment;
import com.team007.appalanche.experiment.NonNegativeCountExperiment;
import com.team007.appalanche.scannableCode.BinomialScannableCode;
import com.team007.appalanche.scannableCode.CountBasedScannableCode;
import com.team007.appalanche.scannableCode.MeasurementScannableCode;
import com.team007.appalanche.scannableCode.NonNegScannableCode;
import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.view.ui.mainActivity.MainActivity;

import java.util.Collections;

/**
 * This is the activity to open when a user chooses to register a barcode
 */
public class RegisterBarcodeActivity extends AppCompatActivity {
    CollectionReference collection = FirebaseFirestore.getInstance().collection("Barcodes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the experiment and barcode from the starting activity
        Intent intent = getIntent();
        Experiment experiment = (Experiment) intent.getSerializableExtra("Experiment");
        String barcode = intent.getStringExtra("Barcode");

        String trialType = experiment.getTrialType();

        Button btnGenerate;
        switch (trialType) {
            case "binomial":
                setContentView(R.layout.fragment_binomial_qr_layout);
                Button btnPass = findViewById(R.id.btn_pass);
                Button btnFail = findViewById(R.id.btn_fail);
                btnFail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BinomialScannableCode binomialBarcode =
                                new BinomialScannableCode(experiment, false, barcode);
                        registerBarcode(barcode, binomialBarcode);
                    }
                });
                btnPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BinomialScannableCode binomialBarcode =
                                new BinomialScannableCode(experiment, true, barcode);
                        registerBarcode(barcode, binomialBarcode);
                    }
                });
                break;
            case "count":
                setContentView(R.layout.fragment_count_qr_layout);
                final EditText inputCount = findViewById(R.id.count_qr_intended_result);
                btnGenerate = findViewById(R.id.btn_generate);
                btnGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CountBasedScannableCode countBarode =
                                new CountBasedScannableCode(experiment, barcode);
                        registerBarcode(barcode, countBarode);
                    }
                });
                break;
            case "measurement":
                setContentView(R.layout.fragment_measurement_qr_layout);
                final EditText inputMeasurement = findViewById(R.id.measurement_qr_intended_result);
                btnGenerate = findViewById(R.id.btn_generate);
                btnGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float intendedMeasurement = Float.parseFloat(inputMeasurement.getText().toString());
                        // call the generateQRCode method from MeasurementExperiment and pass intendedMeasurement as the intended result and save as a MeasurementScannableCode
                        MeasurementScannableCode measurementQRCode =
                                new MeasurementScannableCode(experiment, intendedMeasurement, barcode);
                        registerBarcode(barcode, measurementQRCode);
                    }
                });
                break;
            case "nonNegativeCount":
                setContentView(R.layout.fragment_nonneg_qr_layout);
                final EditText inputNonNeg = findViewById(R.id.nonneg_qr_intended_result);
                btnGenerate = findViewById(R.id.btn_generate);
                btnGenerate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int intendedNonNeg = Integer.parseInt(inputNonNeg.getText().toString());
                        // call the generateQRCode method from NonNegExperiment and pass intendedNonNeg as the intended result and save as a NonNegScannableCode
                        NonNegScannableCode nonNegBarcode = new NonNegScannableCode(experiment,
                                intendedNonNeg, barcode);
                        registerBarcode(barcode, nonNegBarcode);
                    }
                });
                break;
            default:
                //exit
                break;
        }
    }

    /**
     * Given a barcode and barcode object, this method checks that the barcode is not registered
     * in the database and registers it, then it finishes the activity.
     * @param barcode This is the barcode to register.
     * @param barcodeObject This is the object encoding the trial the barcode should resolve to.
     */
    private void registerBarcode(String barcode, ScannableCode barcodeObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterBarcodeActivity.this);
        builder.setTitle("Result of Barcode Registration");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        DocumentReference documentReference = collection.document(barcode);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        builder.setMessage("Could not register the barcode " + barcode + " as it " +
                                "is already in use.");
                        builder.show();
                    } else {
                        documentReference.set(barcodeObject)
                                .addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        builder.setMessage("You have successfully registered the barcode " + barcode + ".");
                                        builder.show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        builder.setMessage("There was an error registering your barcode");
                                        builder.show();
                                    }
                                });
                    }
                } else {
                    builder.setMessage("There was an error registering your barcode");
                    builder.show();
                }
            }
        });
    }
}

