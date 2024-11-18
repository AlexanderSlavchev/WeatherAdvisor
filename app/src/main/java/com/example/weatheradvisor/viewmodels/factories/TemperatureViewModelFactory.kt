package com.example.weatheradvisor.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatheradvisor.utils.ErrorMessages.ERROR_UNKNOWN_VIEWMODEL_CLASS
import com.example.weatheradvisor.viewmodels.TemperatureViewModel

class TemperatureViewModelFactory(private val application: android.app.Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TemperatureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TemperatureViewModel(application) as T
        }
        throw IllegalArgumentException(ERROR_UNKNOWN_VIEWMODEL_CLASS)
    }
}