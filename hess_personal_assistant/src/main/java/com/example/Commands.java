package com.example;

import com.example.APIs.ChatGPT;
import com.example.APIs.Food;
import com.example.APIs.Quotes;
import com.example.APIs.RecentNews;
import com.example.APIs.Timer;
import com.example.APIs.Weather;

public class Commands {

    //Will search the KeyWord for a specific keyword to trigger outside events 
    public static void processCommand(String command){
        try{
            command = command.toLowerCase();    

            //Check to see if the command contains a specific keyword, otherwise it will send the prompt to chatGPT
            if(command.contains("weather")){
                //Access the openweather API to get the weather.
                System.out.println(Weather.getWeather());
            }
            else if(command.contains("news")){
                System.out.println(RecentNews.getNews());
            }
            else if(command.contains("food")){
                System.out.println(Food.getRandomMeal());
            }
            else if(command.contains("timer")){
                Timer.startTimer(command);
            }
            else if(command.contains("quote")){
                System.out.println(Quotes.getRandomQuote());
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
