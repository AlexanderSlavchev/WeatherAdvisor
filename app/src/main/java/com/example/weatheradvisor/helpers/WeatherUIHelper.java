package com.example.weatheradvisor.helpers;

import android.view.View;
import android.widget.TextView;

import com.example.weatheradvisor.R;
import com.example.weatheradvisor.models.WeatherResponse;

import java.util.Locale;

public class WeatherUIHelper {

    public static void updateUI(View view, WeatherResponse weatherResponse) {
        TextView temperatureTextView = view.findViewById(R.id.temperature_text_view);
        TextView weatherDescriptionTextView = view.findViewById(R.id.weather_description_text_view);
        TextView recommendationTextView = view.findViewById(R.id.recommendation_text_view);

        double temp = weatherResponse.getMain().getTemp();
        String weatherCondition = weatherResponse.getWeather().get(0).getDescription();

        temperatureTextView.setText(String.format(Locale.getDefault(), "%.1f°C", temp));
        weatherDescriptionTextView.setText(weatherCondition);

        String recommendation = ClothingRecommendation.getRecommendation(temp, weatherCondition);
        recommendationTextView.setText(recommendation);
    }
}