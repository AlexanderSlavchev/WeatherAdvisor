package com.example.weatheradvisor.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatheradvisor.BuildConfig;
import com.example.weatheradvisor.models.WeatherResponse;
import com.example.weatheradvisor.services.WeatherApiClient;
import com.example.weatheradvisor.helpers.LocationHelper;
import com.example.weatheradvisor.services.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<WeatherResponse> weatherData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final WeatherService weatherService;

    public WeatherViewModel() {
        weatherService = WeatherApiClient.getInstance();
    }

    public LiveData<WeatherResponse> getWeatherData() {
        return weatherData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchWeatherData(Context context) {
        LocationHelper locationHelper = new LocationHelper(context);
        locationHelper.getLastLocation(location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                weatherService.getWeather(lat, lon, BuildConfig.weatherApiKey, "metric").enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            weatherData.postValue(response.body());
                        } else {
                            errorMessage.postValue("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        errorMessage.postValue("API error: " + t.getMessage());
                    }
                });
            } else {
                errorMessage.postValue("Unable to get location.");
            }
        });
    }
}