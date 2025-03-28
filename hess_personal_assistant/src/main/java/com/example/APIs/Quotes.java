package com.example.APIs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quotes {

    // List of predefined quotes
    private static final List<String> QUOTES = new ArrayList<>();

    static {
        // Initialize the list with 20 quotes
        //List generated using ChatGPT, (Im not that creative)
        QUOTES.add("The only limit to our realization of tomorrow is our doubts of today. - Franklin D. Roosevelt");
        QUOTES.add("Do what you can, with what you have, where you are. - Theodore Roosevelt");
        QUOTES.add("Success is not final, failure is not fatal: It is the courage to continue that counts. - Winston Churchill");
        QUOTES.add("Believe you can and you're halfway there. - Theodore Roosevelt");
        QUOTES.add("Act as if what you do makes a difference. It does. - William James");
        QUOTES.add("What lies behind us and what lies before us are tiny matters compared to what lies within us. - Ralph Waldo Emerson");
        QUOTES.add("Keep your face always toward the sunshine—and shadows will fall behind you. - Walt Whitman");
        QUOTES.add("The best way to predict the future is to create it. - Peter Drucker");
        QUOTES.add("You are never too old to set another goal or to dream a new dream. - C.S. Lewis");
        QUOTES.add("It always seems impossible until it’s done. - Nelson Mandela");
        QUOTES.add("Happiness is not something ready made. It comes from your own actions. - Dalai Lama");
        QUOTES.add("In the middle of every difficulty lies opportunity. - Albert Einstein");
        QUOTES.add("Life is 10% what happens to us and 90% how we react to it. - Charles R. Swindoll");
        QUOTES.add("Your time is limited, don’t waste it living someone else’s life. - Steve Jobs");
        QUOTES.add("The purpose of our lives is to be happy. - Dalai Lama");
        QUOTES.add("Turn your wounds into wisdom. - Oprah Winfrey");
        QUOTES.add("The best revenge is massive success. - Frank Sinatra");
        QUOTES.add("Don’t count the days, make the days count. - Muhammad Ali");
        QUOTES.add("The future belongs to those who believe in the beauty of their dreams. - Eleanor Roosevelt");
        QUOTES.add("Do not wait to strike till the iron is hot; but make it hot by striking. - William Butler Yeats");
        QUOTES.add("Comparison is the thief of joy. - Theodore Roosevelt");
    }

    // Method to get a random quote
    public static String getRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(QUOTES.size()); // Generate a random index between 0 and 20
        return QUOTES.get(index); // Return the quote at the random index
    }
}