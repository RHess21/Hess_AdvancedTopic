package com.example;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonValidator {

    // This method validates the JSON response by checking for required fields.
    // It returns true if all required fields are present, otherwise false.
    // The method uses Gson library to parse the JSON response.
    // Used to verify that the responses we get from the APIs are valid and contain the right fields. 
    public static boolean validateFields(String jsonResponse, String... requiredFields) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            for (String field : requiredFields) {
                if (!jsonObject.has(field)) {
                    return false; // Missing required field
                }
            }

            return true; // All required fields are present
        } catch (Exception e) {
            LoggerUtil.logError(e, "JSON validation failed");
            return false;
        }
    }
}