package com.example.weatheradvisor.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.weatheradvisor.data.local.WeatherData;
import com.example.weatheradvisor.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiClient {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static WeatherService weatherService;

    public static WeatherService getInstance() {
        if (weatherService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherService = retrofit.create(WeatherService.class);
        }
        return weatherService;
    }

    public void fetchWeatherData(String apiKey, String units, double lat, double lon, MutableLiveData<WeatherData> weatherData) {
        getInstance().getWeather(lat, lon, apiKey, units).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    WeatherData data = new WeatherData();
                    data.setTemperature(weatherResponse.getMain().getTemp());
                    data.setDescription(weatherResponse.getWeather().get(0).getDescription());
                    weatherData.postValue(data);
                } else {
                    Log.e("WeatherApiClient", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("WeatherApiClient", "Request failed", t);
            }
        });
    }
}