package com.example;

import com.example.APIs.ChatGPT;
import com.example.APIs.Food;
import com.example.APIs.RecentNews;
import com.example.APIs.Weather;

public class Commands {

    //Will search the KeyWord for a specific keyword to trigger outside events 
    public static void processCommand(String command){
        try{
            command = command.toLowerCase();    
            if(command.contains("weather")){
                //Access the openweather API to get the weather.
                String forecast = Weather.getWeather();
                System.out.println(forecast);
            }
            else if(command.contains("news")){
                System.out.println(RecentNews.getNews());
            }
            else if(command.contains("food")){
                System.out.println(Food.getRandomMeal());
            }
            else if(command.contains("joke")){
                System.out.println("Joke of the day");
            }
            else if(command.contains("quote")){
                System.out.println("Quote of the day");
            }
            else if(command.contains("exit")){
                System.out.println("Exiting program...");
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
