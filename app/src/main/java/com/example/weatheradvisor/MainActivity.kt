package com.example.weatheradvisor

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.weatheradvisor.fragments.MapFragment
import com.example.weatheradvisor.fragments.WeatherFragment
import com.example.weatheradvisor.ui.theme.WeatherAdvisorTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.weatheradvisor.repositories.WeatherData
import com.example.weatheradvisor.repositories.WeatherDatabase


private const val LOCATION_PERMISSION_ERROR = "Location permission is required to use this feature"

class MainActivity : AppCompatActivity() {
    private lateinit var weatherDatabase: WeatherDatabase
    private var temperature: Double? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, LOCATION_PERMISSION_ERROR, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherDatabase = WeatherDatabase.getDatabase(this)

        loadTemperature()
        setContent {
            WeatherAdvisorTheme {
                temperature?.let { temp ->
                    MainScreen(temp)
                }
            }
        }
        checkLocationPermission()
    }

    private fun loadTemperature() {
        Thread {
            val latestWeatherData = weatherDatabase.weatherDao().getLatestWeatherData()
            temperature = latestWeatherData?.temperature

            // Актуализиране на UI на главния поток
            runOnUiThread {
                setContent {
                    WeatherAdvisorTheme {
                        temperature?.let {
                            MainScreen(it)
                        }
                    }
                }
            }
        }.start()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

            }

@Composable
fun MainScreen(d: Double) {
    var selectedScreen by remember { mutableStateOf("weather") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Info, contentDescription = "Weather") },
                    label = { Text("Weather") },
                    selected = selectedScreen == "weather",
                    onClick = { selectedScreen = "weather" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.LocationOn, contentDescription = "Map") },
                    label = { Text("Map") },
                    selected = selectedScreen == "map",
                    onClick = { selectedScreen = "map" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                "weather" -> WeatherFragmentContainer(d)
                "map" -> MapFragmentContainer()
            }
        }
    }
}

@Composable
fun WeatherFragmentContainer(temperature: Double) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val fragmentManager: FragmentManager = activity.supportFragmentManager

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween, // Разпределя елементите с максимално пространство между тях
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Показване на WeatherFragment (информацията за времето)
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { ctx ->
                    val container = FragmentContainerView(ctx).apply {
                        id = View.generateViewId()
                    }
                    fragmentManager.beginTransaction()
                        .replace(container.id, WeatherFragment())
                        .commitAllowingStateLoss()
                    container
                }
            )

            // Изображение на дрехите, позиционирано в долната част

            ClothingAdviceUI(
                temperature = temperature,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp) // Допълнителен падинг за отстояние от горния компонент
            )
        }
    }
}

@Composable
fun MapFragmentContainer() {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val fragmentManager: FragmentManager = activity.supportFragmentManager

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val container = FragmentContainerView(ctx).apply {
                id = View.generateViewId()
            }
            fragmentManager.beginTransaction()
                .replace(container.id, MapFragment())
                .commitAllowingStateLoss()
            container
        }
    )
}

@Composable
fun ClothingAdviceUI(temperature: Double, modifier: Modifier) {
    // Логика за избор на изображение според температурата
    val clothingImage = when {
        temperature < 0 -> R.drawable.ic_heavy_coat // Зимно яке
        temperature in 0.0..10.0 -> R.drawable.ic_light_coat_and_hat // Леко яке
        temperature in 10.0..20.0 -> R.drawable.ic_light_jacket // Пуловер
        temperature > 20 -> R.drawable.ic_tshirt // Тениска
        else -> R.drawable.ic_heavy_coat // По подразбиране
    }

    // Показване на изображението на дрехите с пълна ширина
    Image(
        painter = painterResource(id = clothingImage),
        contentDescription = "Clothing suggestion",
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 150.dp) // Минимална височина за изображението
    )
}








