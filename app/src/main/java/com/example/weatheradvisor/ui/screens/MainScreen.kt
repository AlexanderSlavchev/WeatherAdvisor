package com.example.weatheradvisor.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.weatheradvisor.ui.components.AppNavigationBar

@Composable
fun MainScreen(temperature: Double) {
    var selectedScreen by remember { mutableStateOf("weather") }

    Scaffold(
        bottomBar = {
            AppNavigationBar(selectedScreen = selectedScreen) { selectedScreen = it }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                "weather" -> WeatherScreen(temperature)
                "map" -> MapScreen()
            }
        }
    }
}