package com.example.weatheradvisor.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import com.example.weatheradvisor.helpers.LocationHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapScreen(locationHelper: LocationHelper) {
    val context = LocalContext.current

    AndroidView(factory = { ctx ->
        val fragmentContainerView = FragmentContainerView(ctx).apply {
            id = View.generateViewId()
        }
        val mapFragment = SupportMapFragment.newInstance()
        val fragmentManager = (ctx as AppCompatActivity).supportFragmentManager
        fragmentManager.beginTransaction().add(fragmentContainerView.id, mapFragment).commit()
        mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
            // Получаваме локацията чрез LocationHelper
            locationHelper.getLastLocation { location ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                    googleMap.addMarker(MarkerOptions().position(currentLocation).title("Your Location"))
                }
            }
        })
        fragmentContainerView
    })
}