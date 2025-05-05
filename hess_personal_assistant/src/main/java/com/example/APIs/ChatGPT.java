package com.example.APIs;

import com.example.Keys;
import com.example.LoggerUtil;
import com.example.JsonValidator;

import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPT {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    // This method sends a prompt to the ChatGPT API and returns the response
    // It uses the HttpURLConnection class to make a POST request to the API
    // Sourced from ChatGPTs API documentation and examples, with the help of ChatGPT itself.
    // Finished the prompt engineering to keep responses short as to not use up the api limits.
    public static String getResponseGPT(String prompt) {
        try {
            // Create the connection
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + Keys.ChatGPTKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload
            String payload = String.format(
                "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [" +
                "{\"role\": \"system\", \"content\": \"You are a helpful assistant who keeps their response under 3 sentences.\"}," +
                "{\"role\": \"user\", \"content\": \"%s\"}" + "] }",
                prompt
            );


            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.getBytes());
                os.flush();
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    // Extract the response content
                    return parseResponse(response.toString());
                }
            } else {
                LoggerUtil.logError(new Exception("HTTP error code: " + responseCode), "Error during API call to ChatGPT");
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.logError(e, "Error during API call to ChatGPT");
            return "Sorry, I dont have an answer for that yet.";
        }
    }

    // Helper method to parse the JSON response
    // This method uses a simple JSON parser to extract the content of the response
    private static String parseResponse(String jsonResponse) {
        // Extract the content of the response using a simple JSON parser
        try {
            //Verify the JSON response is valid
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                return "Error: Empty response from API.";
            }
            if (JsonValidator.validateFields(jsonResponse, "choices", "message", "content")) {
                return "Error: Invalid response format.";
            }
            // Using Gson library to parse the JSON response
            com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(jsonResponse).getAsJsonObject();
            return jsonObject
                .getAsJsonArray("choices")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("message")
                .get("content")
                .getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response.";
        }
    }
}