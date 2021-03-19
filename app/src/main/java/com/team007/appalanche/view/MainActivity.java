package com.team007.appalanche.view;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.google.gson.Gson;
import com.team007.appalanche.R;
import com.team007.appalanche.scannableCode.ScannableCode;
import com.team007.appalanche.view.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    // FirebaseFirestore db; // TODO: hook up firebase
    Button btnScanQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton addExperimentButton = findViewById(R.id.addExperimentButton);
        FloatingActionButton scanCodeButton = findViewById(R.id.scanCodeButton);

        AccountManager am = AccountManager.get(this); // "this" references the current Context

        Account[] accounts = am.getAccountsByType("com.google");

        addExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Im a new experiment", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        scanCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

        btnScanQRCode = findViewById(R.id.scan_qr_code);
        btnScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void scanCode() {
        // scan the code from the zxing library
        // deserialize
        // get the trial from the deserialization
        // add the trial to the appropriate experiment
        // go to that experiment
    }

    @Override
    // Followed the tutorial from Coding in Flow on Youtube.com
    // Video URL: https://www.youtube.com/watch?v=oh4YOj9VkVE&ab_channel=CodinginFlow
    /**
     * This sets up a custom menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    // Followed the tutorial from Coding in Flow on Youtube.com
    // Video URL: https://www.youtube.com/watch?v=oh4YOj9VkVE&ab_channel=CodinginFlow
    /**
     * This reroutes the selected menu options to callback functions
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_profile:
                openProfileActivity();
                break;
            case R.id.app_bar_search:
                searchActivity();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Takes in the serialized string of a barcode and returns the barcode object
     * @param serialBarcode
     * @return Returns the deserialized barcode object
     */
    private ScannableCode deserialize(String serialBarcode) {
        Gson gson = new Gson();
        return gson.fromJson(serialBarcode, ScannableCode.class);
    }

    private void openProfileActivity() {
        //TODO: Implement
    }

    private void searchActivity() {
        //TODO: Implement
    }

}