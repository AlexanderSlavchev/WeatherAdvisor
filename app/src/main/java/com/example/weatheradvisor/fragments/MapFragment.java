package com.example.weatheradvisor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.weatheradvisor.R;
import com.example.weatheradvisor.helpers.LocationHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        updateLocationOnMap();
    }

    private void updateLocationOnMap() {
        LocationHelper locationHelper = new LocationHelper(requireContext());
        locationHelper.getLastLocation(location -> {
            if (location != null && googleMap != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
            }
        });
    }
}