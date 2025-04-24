package com.example;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonValidator {

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