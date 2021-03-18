package com.team007.appalanche.view;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
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
import android.widget.ListView;

import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.R;
import com.team007.appalanche.custom.CustomList;
import com.team007.appalanche.view.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // FirebaseFirestore db; // TODO: hook up firebase

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
                Snackbar.make(view, "Im a QR scanner", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    private void openProfileActivity() {
        //TODO: Implement
    }

    private void searchActivity() {
        //TODO: Implement
    }

}