package com.example.weatheradvisor;

import androidx.lifecycle.MutableLiveData;

import com.example.weatheradvisor.repositories.WeatherData;

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
    public void fetchWeatherData(String city, MutableLiveData<WeatherData> weatherData) {
        // Извършете заявката към API тук и запишете данните
        // Пример:
        WeatherData data = new WeatherData();
        data.setTemperature(15);
        weatherData.postValue(data);
    }
}