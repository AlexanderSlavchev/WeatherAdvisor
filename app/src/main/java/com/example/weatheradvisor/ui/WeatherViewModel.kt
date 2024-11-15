//package com.example.weatheradvisor.ui
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.weatheradvisor.WeatherApiClient
//import com.example.weatheradvisor.helpers.LocationHelper
//import com.example.weatheradvisor.models.WeatherResponse
//import com.example.weatheradvisor.repositories.WeatherDatabase
//import com.example.weatheradvisor.repositories.WeatherData
//import kotlinx.coroutines.launch
//
//class WeatherViewModel(
//    private val locationHelper: LocationHelper,
//    private val weatherDatabase: WeatherDatabase
//) : ViewModel() {
//
//    val weatherData = mutableStateOf<WeatherResponse?>(null)
//
//    init {
//        fetchWeatherData()
//    }
//
//    private fun fetchWeatherData() {
//        locationHelper.getLastLocation { location ->
//            if (location != null) {
//                val lat = location.latitude
//                val lon = location.longitude
//
//                viewModelScope.launch {
//                    try {
//                        val response = WeatherApiClient.getInstance().getWeather(lat, lon, "your_api_key", "metric")
//                        if (response.isSuccessful) {
//                            weatherData.value = response.body()
//                            cacheWeatherData(response.body())
//                        } else {
//                            loadCachedWeatherData()
//                        }
//                    } catch (e: Exception) {
//                        loadCachedWeatherData()
//                    }
//                }
//            }
//        }
//    }
//
//    private fun cacheWeatherData(weatherResponse: WeatherResponse?) {
//        weatherResponse?.let {
//            val weatherData = WeatherData(
//                temperature = it.main.temp,
//                description = it.weather[0].description,
//                timestamp = System.currentTimeMillis()
//            )
//
//            viewModelScope.launch {
//                weatherDatabase.weatherDao().insert(weatherData)
//            }
//        }
//    }
//
//    private fun loadCachedWeatherData() {
//        viewModelScope.launch {
//            val cachedData = weatherDatabase.weatherDao().getLatestWeatherData()
//            cachedData?.let {
//                weatherData.value = WeatherResponse(
//                    main = WeatherResponse.Main(temp = it.temperature),
//                    weather = listOf(WeatherResponse.Weather(description = it.description)),
//                    // попълни и останалите полета, ако са нужни
//                )
//            }
//        }
//    }
//}