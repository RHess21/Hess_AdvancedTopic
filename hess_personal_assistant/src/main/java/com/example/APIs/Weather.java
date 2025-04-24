package com.example.APIs;

import com.example.Keys;
import com.example.LoggerUtil;
import com.google.gson.Gson;
import com.example.JsonValidator;

import java.util.List;


public class Weather {
    //OpenWeatherMap API Url
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static String getWeather() {
        try {
            // Build the API URL
            //HARD CODED THE LAT AND LONG FOR NOW, These are Williamsports coordinates.
            String urlString = String.format("%s?lat=%f&lon=%f&appid=%s&units=imperial", API_URL, 41.2412, -77.0011, Keys.Weatherkey);
            
            //Open connection to api and return the json response as a string.
            String response = Connection.fetchApiResponse(urlString);

            //Validate the JSON response to ensure it contains the required fields
            if (!JsonValidator.validateFields(response, "main", "weather", "name")) {
                return "Sorry, I couldn't retrieve the weather information. Please try again later.";
            }

            // Parse the JSON response using Gson
            Gson gson = new Gson();
            WeatherResponse weatherResponse = gson.fromJson(response, WeatherResponse.class);

            // Extract the desired fields from the json response with the help of GSON
            String location = weatherResponse.name;
            double temperature = weatherResponse.main.temp;
            String description = weatherResponse.weather.get(0).description;

            // Format the output as "It is 57°F and clear in Williamsport"
            return String.format("It is %.1f°F and %s in %s", temperature, description, location);
            
        } catch (Exception e) {
            LoggerUtil.logError(e, "Error during weather API call");
            return "Im sorry, I was unable to get the weather at this time.";
        }
    }

    // Nested WeatherResponse class
    //Private because it is only needed for the weather class
    //This class is used with GSON to parse the APIs JSON response into variables for later use. 
    /*
     * Saves all of the data but can be narrowed down to just what is used. 
     * I wanted to keep most of it for future possible use. 
     */
    private static class WeatherResponse {
        private Main main;
        private List<WeatherForecast> weather;
        private String name;

        private static class Main {
            private double temp;
            private double feels_like;
            private double temp_min;
            private double temp_max;
            private int pressure;
            private int humidity;
        }

        private static class WeatherForecast {
            private String main;
            private String description;
        }
    }
//End of weather class    
}