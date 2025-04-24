package com.example.APIs;

import java.util.List;

import com.example.LoggerUtil;
import com.google.gson.Gson;
import com.example.JsonValidator;


public class Food {
    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/random.php";
    private static final String API_SpecificURL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

    // This is to save the meal ID in case they want the recipe or ingredients
    public static String mealID;

    public static String getRandomMeal() {
        try {
            // Calls the fetchApiresponse method to connect and return the JSON response as a string
            String response = Connection.fetchApiResponse(API_URL);

            // Validate the JSON response to ensure it contains the required fields
            if (!JsonValidator.validateFields(response, "meals")) {
                return "Sorry, I couldn't retrieve the meal information. Please try again later.";
            }

            // Parse the JSON response using Gson just like other APIs
            Gson gson = new Gson();
            MealResponse mealResponse = gson.fromJson(response, MealResponse.class);

            // Return the strMeal from the first meal in the response
            String mealName = mealResponse.getMeals().get(0).strMeal;
            mealID = mealResponse.getMeals().get(0).idMeal;
            return String.format("%s, would be a good meal for you!", mealName);
            
        } catch (Exception e) {
            // Log the error using LoggerUtil
            LoggerUtil.logError(e, "Error during API call to Food API");
            return "Sorry, I could not find a meal for you.";
        }
    }
    /*
     * Returns the ingredients and instructions for the meal that was recomended last
     * by the getRandomMeal method.
     */
    public static String getMealInfo() {
        try {
            // Calls the fetchApiresponse method to connect and return the JSON response as a string
            String response = Connection.fetchApiResponse(API_SpecificURL + mealID);
    
            // Parse the JSON response using Gson
            Gson gson = new Gson();
            MealResponse mealResponse = gson.fromJson(response, MealResponse.class);
    
            // Get the first meal from the response
            Meal meal = mealResponse.getMeals().get(0);
    
            // Extract the ingredients and instructions
            String instructions = meal.strInstructions;
            StringBuilder ingredients = new StringBuilder("Ingredients:\n");
    
            // Loop through the meal fields to dynamically extract ingredients
            for (int i = 1; i <= 20; i++) {
                String ingredient = (String) Meal.class.getDeclaredField("strIngredient" + i).get(meal);
                String measure = (String) Meal.class.getDeclaredField("strMeasure" + i).get(meal);
    
                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredients.append("- ").append(ingredient);
                    if (measure != null && !measure.isEmpty()) {
                        ingredients.append(" (").append(measure).append(")");
                    }
                    ingredients.append("\n");
                }
            }
    
            // Combine the ingredients and instructions into a single response
            return ingredients.toString() + "\nInstructions:\n" + instructions;
    
        } catch (Exception e) {
            // Log the error using LoggerUtil
            LoggerUtil.logError(e, "Error during API call to Food API");
            return "Sorry, I could not retrieve the meal information.";
        }
    }

    private static class MealResponse {
        private List<Meal> meals;

        public List<Meal> getMeals() {
            return meals;
        }
    }
    // This class represents the structure of the meal object in the JSON response
    // It contains all the fields that are returned by the API for a meal.
    // The fields are private because they are only needed within this class.
    // The Gson library will automatically map the JSON fields to these class fields.
    
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