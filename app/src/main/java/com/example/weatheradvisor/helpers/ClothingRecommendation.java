package com.example.weatheradvisor.helpers;

public class ClothingRecommendation {
    public static String getRecommendation(double temperature, String weatherCondition) {
        if (temperature < 0) {
            return "It's freezing! Wear a heavy coat, scarf, gloves, and a hat.";
        } else if (temperature < 10) {
            return "Wear a coat and a hat to stay warm.";
        } else if (temperature < 20) {
            return "A light jacket or sweater should be enough.";
        } else if (temperature >= 20 && temperature < 30) {
            if (weatherCondition.contains("rain")) {
                return "It's warm, but take an umbrella just in case.";
            } else if (weatherCondition.contains("sunny")) {
                return "Perfect weather for light clothing and sunglasses.";
            } else {
                return "Light clothing should be enough.";
            }
        } else if (temperature >= 30) {
            return "It's hot! Wear shorts, a t-shirt, and stay hydrated.";
        }

        if (weatherCondition.contains("rain")) {
            return "Don't forget an umbrella or a raincoat.";
        }

        return "Light clothing is enough.";
    }
}