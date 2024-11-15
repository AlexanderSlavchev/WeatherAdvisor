package com.example.weatheradvisor.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherData weatherData);

    @Query("SELECT * FROM weather_data ORDER BY timestamp DESC LIMIT 1")
    WeatherData getLatestWeatherData();
}