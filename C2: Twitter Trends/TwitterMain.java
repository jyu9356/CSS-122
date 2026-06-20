// Jeffrey Yu
// 05/18/2026
// CSE 122
// C2: TwitterTrends
// TA: Colin Lim

// This class serves as the client for the TwitterTrends program.
// It creates a TweetBot from a list of cat tweets and uses TwitterTrends
// to analyze and display the most frequent word and most popular tweet.

import java.util.*;
import java.io.*;

public class TwitterMain {
    public static void main(String[] args) throws FileNotFoundException {
        // Create and print a list of Tweet objects from the cat folder
        List<Tweet> catTweets = scrapeTweets(); 
        for (Tweet tweet : catTweets) {
            System.out.println(tweet);
            System.out.println();
        }

        // Instantiate TweetBot 
        TweetBot catBot = new TweetBot(catTweets);

        // Instantiate TwitterTrends Object
        TwitterTrends trends = new TwitterTrends(catBot);

        // Call and print getMostFrequentWord()
        String mostFrequent = trends.getMostFrequentWord();
        System.out.println("=== Most Frequent Word ===");
        System.out.println("The most frequently used word across all tweets is: \"" + mostFrequent + "\"");
        System.out.println();

        // Call getMostPopularTweet() extension and print results
        Tweet mostPopular = trends.getMostPopularTweet();
        System.out.println("=== Most Popular Tweet ===");
        System.out.println("The most popular tweet (by likes + retweets) is:");
        System.out.println(mostPopular);
        System.out.println("Total engagement: " + (mostPopular.getLikes() + mostPopular.getRetweets()));
        System.out.println();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // PROVIDED SETUP CODE ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs a new list with the Tweet objects based on the content.
     * found in the cats folder and tweet_info file.
     *
     * @return the list of tweets
     */
    public static List<Tweet> scrapeTweets() throws FileNotFoundException {
        List<Tweet> tweets = new ArrayList<>();
        Scanner instagramScraper = new Scanner(new File("cats/tweet_info.txt"));
        while(instagramScraper.hasNext()) {
            String title = instagramScraper.nextLine();
            String caption = "";
            String date = "";
            while (!hasDate(date) && instagramScraper.hasNextLine()) {
                String line = instagramScraper.nextLine();
                if (hasDate(line)) { // end of tweet in file
                    date += line;
                    instagramScraper.nextLine();
                } else {
                    caption += "\n" + line;
                }
            }
            caption = caption.substring(1); // remove starting \n
            tweets.add(new Tweet("cats/" + title + ".jpg", caption, date));
        }
        return tweets;
    }

     /**
     * Checks if the provided String line case-insenseitivly contains 
     * one of the twelve months of the year.
     *
     * @param line  The provided line of text to search through
     *
     * @return boolean result for if at least one month is in provided String
     */
    public static boolean hasDate(String line) {
        line = line.toUpperCase();
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
                           "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER",
                           "NOVEMBER", "DECEMBER"};
        for (String month : months) {
            if (line.contains(month)) {
                return true;
            }
        }
        return false;
    }
}
