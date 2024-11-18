package com.example.weatheradvisor.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.weatheradvisor.data.local.WeatherDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TemperatureViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherDatabase = WeatherDatabase.getDatabase(application)

    private val _temperature = MutableLiveData<Double?>()
    val temperature: LiveData<Double?> get() = _temperature

    fun loadTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            val latestWeatherData = weatherDatabase.weatherDao().getLatestWeatherData()
            _temperature.postValue(latestWeatherData?.temperature)
        }
    }
}