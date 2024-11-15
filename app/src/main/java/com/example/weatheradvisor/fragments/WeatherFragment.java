package com.example.weatheradvisor.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.weatheradvisor.BuildConfig;
import com.example.weatheradvisor.R;
import com.example.weatheradvisor.WeatherApiClient;
import com.example.weatheradvisor.WeatherService;
import com.example.weatheradvisor.helpers.ClothingRecommendation;
import com.example.weatheradvisor.helpers.LocationHelper;
import com.example.weatheradvisor.models.WeatherResponse;
import com.example.weatheradvisor.repositories.WeatherData;
import com.example.weatheradvisor.repositories.WeatherDatabase;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {
    public static final String TEMPERATURE_FORMAT = "%.1f°C";
    // take the api key from local.properties file
    private WeatherDatabase weatherDatabase;
    private WeatherService weatherService;
    private LocationHelper locationHelper;
    private TextView temperatureTextView;
    private TextView weatherDesctriptionTextView;
    private TextView recommendationTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        // Инициализация на елементите от интерфейса
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
        weatherDesctriptionTextView = view.findViewById(R.id.weather_description_text_view);
        recommendationTextView = view.findViewById(R.id.recommendation_text_view);

        // Инициализация на база данни, API клиент и локация
        weatherDatabase = WeatherDatabase.getDatabase(requireContext());
        weatherService = WeatherApiClient.getInstance();
        locationHelper = new LocationHelper(requireContext());

        // Извличане на текущата локация и данни за времето
        fetchWeatherData();

        return view;
    }



    private void fetchWeatherData() {
        locationHelper.getLastLocation(location -> {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                Call<WeatherResponse> call = weatherService.getWeather(lat, lon, BuildConfig.weatherApiKey, "metric");
                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            WeatherResponse weatherResponse = response.body();
                            Log.d("WeatherFragment", "API Response: " + weatherResponse.toString()); // Лог за успешен отговор
                            updateUI(weatherResponse);
                            cacheWeatherData(weatherResponse);
                        } else {
                            try {
                                Log.e("WeatherFragment", "API Request failed: " + response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("WeatherFragment", "API Error: " + t.getMessage(), t);
                        loadCachedWeatherData();
                    }
                });
            }
        });
    }

    private void updateUI(WeatherResponse weatherResponse) {
        double temp = weatherResponse.getMain().getTemp();
        String weatherDescription = weatherResponse.getWeather().get(0).getMain();
        String weatherCondition = weatherResponse.getWeather().get(0).getDescription();

        temperatureTextView.setText(String.format(Locale.getDefault(), TEMPERATURE_FORMAT, temp));
        String recommendation = ClothingRecommendation.getRecommendation(temp, weatherCondition);
        weatherDesctriptionTextView.setText(weatherCondition);
        recommendationTextView.setText(recommendation);
    }

    private void cacheWeatherData(WeatherResponse weatherResponse) {
        WeatherData weatherData = new WeatherData();
        weatherData.setTemperature(weatherResponse.getMain().getTemp());
        weatherData.setDescription(weatherResponse.getWeather().get(0).getDescription());
        weatherData.setTimestamp(System.currentTimeMillis());

        new Thread(() -> weatherDatabase.weatherDao().insert(weatherData)).start();
    }

    private void loadCachedWeatherData() {
        new Thread(() -> {
            WeatherData cachedData = weatherDatabase.weatherDao().getLatestWeatherData();
            if (cachedData != null) {
                getActivity().runOnUiThread(() -> {
                    temperatureTextView.setText(String.format(Locale.getDefault(), TEMPERATURE_FORMAT, cachedData.getTemperature()));
                    recommendationTextView.setText(ClothingRecommendation.getRecommendation(cachedData.getTemperature(), cachedData.getDescription()));
                });
            }
        }).start();
    }


}




