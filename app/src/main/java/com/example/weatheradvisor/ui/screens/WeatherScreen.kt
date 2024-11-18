package com.example.weatheradvisor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.weatheradvisor.fragments.WeatherFragment
import com.example.weatheradvisor.ui.components.ClothingAdviceUI

@Composable
fun WeatherScreen(temperature: Double) {
    val activity = androidx.compose.ui.platform.LocalContext.current as FragmentActivity
    val fragmentManager: FragmentManager = activity.supportFragmentManager

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { ctx ->
                val container = FragmentContainerView(ctx).apply { id = android.view.View.generateViewId() }
                fragmentManager.beginTransaction()
                    .replace(container.id, WeatherFragment())
                    .commitAllowingStateLoss()
                container
            }
        )

        ClothingAdviceUI(
            temperature = temperature,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}