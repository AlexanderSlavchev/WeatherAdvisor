package com.example.weatheradvisor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatheradvisor.R;
import com.example.weatheradvisor.helpers.WeatherUIHelper;
import com.example.weatheradvisor.viewmodels.TemperatureViewModel;
import com.example.weatheradvisor.viewmodels.WeatherViewModel;

public class WeatherFragment extends Fragment {
    private WeatherViewModel weatherViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        observeWeatherData(view);
        weatherViewModel.fetchWeatherData(requireContext());

        return view;
    }

    private void observeWeatherData(View view) {
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherResponse -> {
            if (weatherResponse != null) {
                WeatherUIHelper.updateUI(view, weatherResponse);
            }
        });

        weatherViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
              Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}