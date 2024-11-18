package com.example.weatheradvisor.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.weatheradvisor.R

@Composable
fun AppNavigationBar(selectedScreen: String, onScreenSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Weather") },
            label = { Text("Weather") },
            selected = selectedScreen == "weather",
            onClick = { onScreenSelected("weather") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map") },
            label = { Text("Map") },
            selected = selectedScreen == "map",
            onClick = { onScreenSelected("map") }
        )
    }
}