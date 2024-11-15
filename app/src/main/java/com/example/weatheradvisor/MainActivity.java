//package com.example.weatheradvisor;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import com.example.weatheradvisor.fragments.MapFragment;
//import com.example.weatheradvisor.fragments.WeatherFragment;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class com.example.weatheradvisor.MainActivity extends AppCompatActivity {
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//        } else {
//            startLocationUpdates();
//        }
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, new WeatherFragment())
//                    .commit();
//        }
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
//            if (item.getItemId() == R.id.navigation_weather) {
//                selectedFragment = new WeatherFragment();
//            } else if (item.getItemId() == R.id.navigation_map) {
//                selectedFragment = new MapFragment();
//            }
//            if (selectedFragment != null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, selectedFragment)
//                        .commit();
//            }
//            return true;
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startLocationUpdates();
//            } else {
//                Log.e("com.example.weatheradvisor.MainActivity", "Location permission not granted");
//            }
//        }
//    }
//
//    private void startLocationUpdates() {
//        Log.d("com.example.weatheradvisor.MainActivity", "Location updates started");
//
//    }
//}