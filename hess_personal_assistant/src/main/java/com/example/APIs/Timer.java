package com.example.APIs;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.LoggerUtil;

public class Timer {

    //Method to start a timer based on user input.
    //The input is expected to be in the format of "5 seconds", "2 minutes", or "1 hour".
    public static void startTimer(String input) {

        try {
            // Regex to extract time amount and unit (e.g., "5 seconds", "2 minutes")
            // Allows for both singluar and plural forms of the time unit.
            Pattern pattern = Pattern.compile("(\\d+)\\s*(seconds?|minutes?|hours?)", Pattern.CASE_INSENSITIVE);

            // Check to see if the pattern matches
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                int amount = Integer.parseInt(matcher.group(1));
                String unit = matcher.group(2).toLowerCase();

                long delayInMillis = 0;

                /*
                 * Sets the delay in milliseconds based on the unit of time
                 * Switch statement checks each case, against the String unit, to determine
                 * which units to use.
                 * No break in between the plural and singular to let both use the same code
                 * Sourced with help from CHATGPT, I was unaware of switches and cases,
                 * I was originally going to use if statements but this is much cleaner
                 */
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
                        System.out.println("Unsupported time unit: " + unit);
                        return;
                }

                System.out.println("Timer started for " + amount + " " + unit + ".");
                /*
                 * This is where the timer is actually started on a separate thread.
                 * The thread sleeps for the amount of time specified in the command then returns
                 */
                try {
                    Thread.sleep(delayInMillis);
                    System.out.println("Time is up!");
                } catch (InterruptedException e) {
                    System.out.println("Timer was interrupted.");
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println(
                        "Invalid input. Please provide time in the format '5 seconds', '2 minutes', or '1 hour'.");
            }
        //Log the error of regex fails
        } catch (Exception e) {
            LoggerUtil.logError(e, "Error during timer processing");
            System.out.println("An error occurred while processing the timer command.");
        }
    }
}
