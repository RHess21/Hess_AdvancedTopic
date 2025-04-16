package com.example.APIs;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.LoggerUtil;
import com.example.TextToSpeech;

public class Timer {

    // Method to start a timer based on user input.
    // The input is expected to be in the format of "5 seconds", "2 minutes", or "1 hour".
    public static void startTimer(String input) {
        try {
            // Regex to extract time amount and unit (e.g., "5 seconds", "2 minutes")
            Pattern pattern = Pattern.compile("(\\d+)\\s*(seconds?|minutes?|hours?)", Pattern.CASE_INSENSITIVE);

            // Check to see if the pattern matches
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                int amount = Integer.parseInt(matcher.group(1));
                String unit = matcher.group(2).toLowerCase();

                final long delayInMillis;

                // Sets the delay in milliseconds based on the unit of time
                switch (unit) {
                    case "second":
                    case "seconds":
                        delayInMillis = TimeUnit.SECONDS.toMillis(amount);
                        break;
                    case "minute":
                    case "minutes":
                        delayInMillis = TimeUnit.MINUTES.toMillis(amount);
                        break;
                    case "hour":
                    case "hours":
                        delayInMillis = TimeUnit.HOURS.toMillis(amount);
                        break;
                    default:
                        try {
                            TextToSpeech.synthesizeText("Unsupported time unit: " + unit);
                        } catch (Exception e) {
                            LoggerUtil.logError(e, "Error during speech for unsupported time unit");
                        }
                        return;
                }

                try {
                    TextToSpeech.synthesizeText("Timer started for " + amount + " " + unit + ".");
                } catch (Exception e) {
                    LoggerUtil.logError(e, "Error during speech for timer start");
                }

                // Start the timer on a separate thread
                new Thread(() -> {
                    try {
                        Thread.sleep(delayInMillis);
                        // Speak "Time is up!" on the main thread after the timer finishes
                        try {
                            TextToSpeech.synthesizeText("Time is up!");
                        } catch (Exception e) {
                            LoggerUtil.logError(e, "Error during speech for timer completion");
                        }
                    } catch (InterruptedException e) {
                        try {
                            TextToSpeech.synthesizeText("Timer was interrupted.");
                        } catch (Exception ex) {
                            LoggerUtil.logError(ex, "Error during speech for timer interruption");
                        }
                        Thread.currentThread().interrupt();
                    }
                }).start();

            } else {
                try {
                    TextToSpeech.synthesizeText(
                            "Invalid input. Please provide time in the format '5 seconds', '2 minutes', or '1 hour'.");
                } catch (Exception e) {
                    LoggerUtil.logError(e, "Error during speech for invalid input");
                }
            }
        } catch (Exception e) {
            LoggerUtil.logError(e, "Error during timer processing");
            try {
                TextToSpeech.synthesizeText("An error occurred while processing the timer command.");
            } catch (Exception ex) {
                LoggerUtil.logError(ex, "Error during speech for general error");
            }
        }
    }
}