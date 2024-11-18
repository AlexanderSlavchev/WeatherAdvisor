package com.example.weatheradvisor

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import com.example.weatheradvisor.ui.screens.MainScreen
import com.example.weatheradvisor.ui.theme.WeatherAdvisorTheme
import com.example.weatheradvisor.utils.ErrorMessages.LOCATION_PERMISSION_DENIED
import com.example.weatheradvisor.utils.ErrorMessages.LOCATION_PERMISSION_GRANTED
import com.example.weatheradvisor.utils.PermissionUtils
import com.example.weatheradvisor.viewmodels.TemperatureViewModel

class MainActivity : AppCompatActivity() {
    private val temperatureViewModel: TemperatureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        temperatureViewModel.loadTemperature()
        setContent {
            WeatherAdvisorTheme {
                temperatureViewModel.temperature.observeAsState().value?.let {
                    MainScreen(it)
                }
            }
        }
        if (!PermissionUtils.hasLocationPermission(this)) {
            PermissionUtils.requestLocationPermission(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.handlePermissionResult(
            requestCode,
            grantResults,
            onGranted = {
                Toast.makeText(this, LOCATION_PERMISSION_GRANTED, Toast.LENGTH_SHORT).show()
            },
            onDenied = {
                Toast.makeText(this, LOCATION_PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
            }
        )
    }
}