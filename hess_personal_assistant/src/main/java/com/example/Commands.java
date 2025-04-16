package com.example;

import com.example.APIs.ChatGPT;
import com.example.APIs.Food;
import com.example.APIs.Quotes;
import com.example.APIs.RecentNews;
import com.example.APIs.Timer;
import com.example.APIs.Weather;
import com.example.TextToSpeech;

public class Commands {

    //Will search the KeyWord for a specific keyword to trigger outside events 
    public static void processCommand(String command){
        try{
            command = command.toLowerCase();    

            //Check to see if the command contains a specific keyword, otherwise it will send the prompt to chatGPT
            if(command.contains("weather")){
                //Access the openweather API to get the weather.
                try {
                    TextToSpeech.synthesizeText(Weather.getWeather());
                } catch (Exception ex) {
                    LoggerUtil.logError(ex, "Error during speech for weather data");
                    System.out.println("Error during speech. Please try again.");
                }
            }
            else if(command.contains("news")){
                try{
                    TextToSpeech.synthesizeText(RecentNews.getNews());
                }catch(Exception e){
                    LoggerUtil.logError(e, "Error during speech for news data");
                    System.out.println("Error during speech. Please try again.");
                }
            }
            else if(command.contains("food")){
                try{
                    TextToSpeech.synthesizeText(Food.getRandomMeal());
                }catch(Exception e){
                    LoggerUtil.logError(e, "Error during speech for food data");
                    System.out.println("Error during speech. Please try again.");
                }
            }
            else if(command.contains("timer")){
                Timer.startTimer(command);
            }
            else if(command.contains("quote")){
                try{
                    TextToSpeech.synthesizeText(Quotes.getRandomQuote());
                }catch(Exception e){
                    LoggerUtil.logError(e, "Error during speech for quote data");
                    System.out.println("Error during speech. Please try again.");
                }
            }
            else if(command.contains("exit") || command.contains("goodbye") || command.contains("bye")){
                try{
                    TextToSpeech.synthesizeText("Have a great day, goodbye.");
                }catch(Exception e){
                    LoggerUtil.logError(e, "Error during goodbye speech");
                    System.out.println("Error during speech. Please try again.");
                }
                System.exit(0);
            }
            else{
                //Will hit chatGPT for a response if a keyword is not recognized.
                String response = ChatGPT.getResponseGPT(command);
                System.out.println(response);
            }
        }catch(Exception e){
            LoggerUtil.logError(e, "Error during command processing");
            e.printStackTrace();
        }
    }
}
