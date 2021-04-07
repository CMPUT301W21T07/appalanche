package com.team007.appalanche;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team007.appalanche.experiment.Experiment;
import com.team007.appalanche.trial.Trial;

import java.util.ArrayList;

/**
 * This class represents a GoogleMaps API and is responsible for displaying any Google Maps screem
 * in the application.
 */
public class GeoMap implements OnMapReadyCallback {

    private GoogleMap map;

    private Location geoLocation;
    private ArrayList<Trial> trials;

    private boolean isEditable;
    private boolean markerSet;
    private boolean hasLocationSet;




    /**
     * Constructor of GeoMap that is called when the Map tab of ExperimentActivity is clicked on.
     * @param isEditable
     *      This indicates whether the map is "editable", i.e, you can change the location of the
     *      marker.
     */
    public GeoMap(Experiment experiment, boolean isEditable) {
        this.isEditable = isEditable;
        this.geoLocation.setLocation(experiment.getRegion());
        markerSet = true;
        if (experiment.getLocationRequired() ==  false) {
            hasLocationSet = false;
        } else {
            hasLocationSet = true;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (isEditable) {
            map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng center) {
                    // Clear any previous markers
                    map.clear();

                    geoLocation.setLat(center.latitude);
                    geoLocation.setLon(center.longitude);

                    // Add a marker to the map
                    map.addMarker(new MarkerOptions()
                            .position(center));

                    // Center the screen around the marker
                    map.moveCamera(CameraUpdateFactory.newLatLng(center));

                    markerSet = true;
                }
            });
        } else {
            if (hasLocationSet) {
                LatLng center = new LatLng(geoLocation.getLat(), geoLocation.getLon());

                map.addMarker(new MarkerOptions()
                        .position(center));

                // Add an orange marker for every trial
                for (Trial trial: trials) {
                    double trialLat = trial.getLocationList().getLat();
                    double trialLon = trial.getLocationList().getLon();
                    LatLng trialLocation = new LatLng(trialLat, trialLon);

                    map.addMarker(new MarkerOptions()
                            .position(trialLocation)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                }

               

                float zoomLevel = 13.0f;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel));

            } else {
                // The default region will be set to Edmonton, AB. I got the following values after
                // a couple simple google searches.
                LatLng defaultCenter = new LatLng(53.5439, -113.4923);
                // Land area of Edmonton is 767.85 km^2 = pi*r^2/1000000 m^2
                double radius = 15634;

                map.addMarker(new MarkerOptions()
                        .position(defaultCenter));



                float zoomLevel = 10.0f;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCenter, zoomLevel));
            }
        }
    }

    /**
     * Gets the geolocation that represents the region of the experiment.
     * @return
     *      Returns the geolocation of the experiment.
     */
    public Location getGeoLocation() {
        return geoLocation;
    }

    /**
     * Gets whether a marker is already set on the map or not.
     * @return
     *      Returns true if a marker is set on the map, false otherwise.
     */
    public boolean isMarkerSet() {
        return markerSet;
    }

    /**
     * Displays a circle on the map. Meant to display a circle centered around the marker of the
     * region set by the user.
     * @param center
     *      This represents the center of the circle.
     * @param radius
     *      This represents the radius of the circle in meters.
     */
