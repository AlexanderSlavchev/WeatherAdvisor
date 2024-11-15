package com.example.weatheradvisor.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;


import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationHelper {
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;

    public LocationHelper(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getLastLocation(OnSuccessListener<Location> listener) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(listener)
                    .addOnFailureListener(e -> Log.e("LocationHelper", "Error getting location", e));
        } else {
            Log.e("LocationHelper", "Location permission not granted");
        }
    }

    public void getLastLocation() {
        getLastLocation(location -> {
            if (location != null) {
                Log.d("LocationHelper", "Location: " + location.getLatitude() + ", " + location.getLongitude());
            } else {
                Log.e("LocationHelper", "Location not found");
            }
        });
    }
}