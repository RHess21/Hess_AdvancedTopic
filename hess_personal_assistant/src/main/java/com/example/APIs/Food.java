package com.example.APIs;

import java.util.List;

import com.example.LoggerUtil;
import com.google.gson.Gson;

public class Food {
    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/random.php";

    public static String getRandomMeal() {
        try {
            // Open connection to API and return the JSON response as a string.
            String response = Connection.fetchApiResponse(API_URL);

            // Parse the JSON response using Gson
            Gson gson = new Gson();
            MealResponse mealResponse = gson.fromJson(response, MealResponse.class);

            // Return the strMeal from the first meal in the response
            String mealName = mealResponse.getMeals().get(0).strMeal;
            return String.format("%s, would be a good meal for you!", mealName);
            
        } catch (Exception e) {
            // Log the error using LoggerUtil
            LoggerUtil.logError(e, "Error during API call to Food API");
            return "Sorry, I could not find a meal for you.";
        }
    }

    private static class MealResponse {
        private List<Meal> meals;

        public List<Meal> getMeals() {
            return meals;
        }
    }

    private static class Meal {
        private String idMeal;
        private String strMeal;
        private String strMealAlternate;
        private String strCategory;
        private String strArea;
        private String strInstructions;
        private String strMealThumb;
        private String strTags;
        private String strYoutube;
        private String strSource;
        private String strImageSource;
        private String strCreativeCommonsConfirmed;
        private String dateModified;
    }
}