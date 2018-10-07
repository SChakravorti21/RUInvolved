package com.example.shoumyo.ruinvolved;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shoumyo.ruinvolved.data_sources.AdminApi;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.models.Geolocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    public static final String CLUB_EXTRA_TAG = "club";

    private Club club;
    private AdminApi api;
    private LatLng newLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_update_location);
        this.club = (Club) getIntent().getSerializableExtra(CLUB_EXTRA_TAG);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        api = new AdminApi(this);
        findViewById(R.id.save_location).setOnClickListener(button -> {
            Geolocation geolocation = new Geolocation();
            geolocation.latitude = newLocation.latitude;
            geolocation.longitude = newLocation.longitude;
            geolocation.clubId = club.id;

            api.updatedClubLocation(geolocation)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            updatedLocation -> {
                                if(updatedLocation.latitude != geolocation.latitude
                                        || updatedLocation.longitude != geolocation.longitude) {
                                    Toast.makeText(
                                            this,
                                            "Update failed, please try again",
                                            Toast.LENGTH_LONG
                                    ).show();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Update successful!",
                                            Toast.LENGTH_LONG
                                    ).show();
                                    finish();
                                }
                            },
                            error -> error.printStackTrace()
                    );
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        map.setOnMarkerDragListener(this);

        // Add a marker in Sydney and move the camera
        LatLng clublocation = new LatLng(
                this.club.location.latitude,
                this.club.location.longitude
        );
        newLocation = clublocation;

        map.addMarker(new MarkerOptions()
                .position(clublocation)
                .title(this.club.name)
                .draggable(true)
        );

        map.moveCamera(CameraUpdateFactory.newLatLng(clublocation));
        map.moveCamera(CameraUpdateFactory.zoomTo(16.0f));
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        newLocation = marker.getPosition();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }
}
