package com.team007.appalanche.view.experimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team007.appalanche.Location;
import com.team007.appalanche.R;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.Trial;

import java.util.ArrayList;

/**
 * This activity displays the map, with all the trial markers
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;
//    private ArrayList<Trial> trials;
    private Experiment experiment;
    private UiSettings uiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        Intent intent = getIntent();
//        trials = (ArrayList<Trial>) intent.getSerializableExtra("Trials");
        experiment = (Experiment) intent.getSerializableExtra("Experiment");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*
    Zooming to show all the markers stolen from stackoverflow.com
    user: https://stackoverflow.com/users/1820695/andr
    post: https://stackoverflow.com/a/14828739
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        ArrayList<Trial> trials = experiment.getTrials();

        for(int i = 0 ; i < trials.size(); i++) {
            // Add a marker and move the camera
            Trial trial = trials.get(i);

            Location location = trial.getLocation();

            if (location != null) {
                LatLng latLng = new LatLng(location.getLat(), location.getLon());

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Trial" + i)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                builder.include(latLng);
            }
        }

        CameraUpdate cu;
        try {
            LatLngBounds bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels
            cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        } catch (IllegalStateException e) {
            LatLng latLng = new LatLng(0, 0);
            cu = CameraUpdateFactory.newLatLng(latLng);
        }

        googleMap.moveCamera(cu);
    }

    // Set up the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}