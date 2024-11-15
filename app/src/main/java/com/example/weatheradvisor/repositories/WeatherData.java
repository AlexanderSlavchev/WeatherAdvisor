package com.example.weatheradvisor.repositories;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_data")
public class WeatherData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double temperature;
    private String description;
    private long timestamp;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}