package com.example.APIs;

import java.util.List;

import com.example.Keys;
import com.example.LoggerUtil;
import com.google.gson.Gson;

public class RecentNews {

    private static final String API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=";

    /*
     * This method fetches the latest news headline for the US.
     * It constructs the API URL using the API key from the Keys class.
     * It uses the Connection class to fetch the API response and parses it using Gson.
     * It returns the title of the first article in the response.
     */
    public static String getNews(){
        // Will access the news API to get the latest news.
        // This is a placeholder for the actual implementation.
        try{
            String urlString = String.format("%s%s",API_URL, Keys.NewsKey);

            //Open connection to api and return the json response as a string.
            String response = Connection.fetchApiResponse(urlString);

            // Parse the JSON response using Gson
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(response, NewsResponse.class);

            // Extract the first article's title
            if (newsResponse.articles != null && !newsResponse.articles.isEmpty()) {
                return "Top Headline: " + newsResponse.articles.get(0).title;
            } else {
                return "No news articles available at this time.";
            }
        }catch(Exception e){
            // Log the error using LoggerUtil
            LoggerUtil.logError(e, "Error during API call to News API");
            return "Sorry, I could not find any news for you.";
        }
    }
    /* 
    * This is a private static class to hold the response from the News API.
    * it contains the status, total results, and a list of articles.
    * Each article contains a source, author, title, description, URL, image URL, published date, and content.
    * The source contains an ID and name.
    * This is custom GSON class to parse the JSON response from the News API.
    * Unused variables are for potential later use, saving almost all of the response
    */
    private static class NewsResponse {
        private String status;
        private int totalResults;
        private List<Article> articles;

        private static class Article {
            private Source source;
            private String author;
            private String title;
            private String description;
            private String url;
            private String urlToImage;
            private String publishedAt;
            private String content;

            private static class Source {
                private String id;
                private String name;
            }
        }
    }
}
