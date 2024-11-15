package com.example.weatheradvisor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen(
    temperature: String = "20°C",
    recommendation: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        // Текст за температурата
        Text(
            text = temperature,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        // Текст за препоръката
        Text(
            text = recommendation,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
@Preview
@Composable
fun WeatherScreenPreview() {
    WeatherScreen(
        temperature = "18°C",
        recommendation = "Wear a light jacket."
    )
}