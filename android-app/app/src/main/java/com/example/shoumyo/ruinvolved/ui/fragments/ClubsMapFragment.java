package com.example.shoumyo.ruinvolved.ui.fragments;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.data_sources.ClubsDataSource;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.ui.adapters.ClubListAdapter;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubsMapFragment extends Fragment implements OnMapReadyCallback {

    private final double EQUATOR_LENGTH = 40075000;


    private ClubsDataSource dataSource;

    public ClubsMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ClubsDataSource(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clubs_map, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Set<String> favoritedClubs = SharedPrefsUtils.getFavoritedClubs(getContext());
        List<Integer> ids = favoritedClubs != null
                ? favoritedClubs.stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList())
                : new ArrayList<>();

        if(ids.size() == 0) {
            Toast.makeText(getContext(), "You don't have any clubs saved.", Toast.LENGTH_LONG).show();
            return;
        }

        dataSource.getClubWithIds(ids)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clubs -> this.addMarkers(googleMap, clubs),
                        error -> error.printStackTrace()
                );
    }

    private void addMarkers(GoogleMap map, List<Club> clubs) {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for(Club club : clubs) {
            LatLng clublocation = new LatLng(club.location.latitude, club.location.longitude);
            MarkerOptions marker = new MarkerOptions()
                    .position(clublocation)
                    .title(club.name);
            map.addMarker(marker);
            boundsBuilder.include(clublocation);
        }

        LatLngBounds bounds = boundsBuilder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);

        map.moveCamera(CameraUpdateFactory.zoomTo(2f));
        map.animateCamera(cu);

//        map.animateCamera(CameraUpdateFactory.zoomTo(17f));
    }


}
