package com.example.weatheradvisor.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.weatheradvisor.fragments.MapFragment

@Composable
fun MapScreen() {
    val activity = androidx.compose.ui.platform.LocalContext.current as FragmentActivity
    val fragmentManager: FragmentManager = activity.supportFragmentManager

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val container = FragmentContainerView(ctx).apply { id = android.view.View.generateViewId() }
            fragmentManager.beginTransaction()
                .replace(container.id, MapFragment())
                .commitAllowingStateLoss()
            container
        }
    )
}