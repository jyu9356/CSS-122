// Jeffrey Yu
// 05/18/2026
// CSE 122 
// C2: TwitterTrends
// TA: Colin Lim

// This class analyzes the tweets stored in a TweetBot object.
// It can find the most frequently used word across all tweet captions,
// and identify the most popular tweet based on likes and retweets.

import java.util.*;
import java.io.*;

public class TwitterTrends {
    private TweetBot bot;

    // B - Constructs a new TwitterTrends object that analyzes the given TweetBot.
    // P - bot: the TweetBot containing tweets to analyze
    public TwitterTrends(TweetBot bot) {
        this.bot = bot;
    }

    // B - Returns the most frequently used word across all tweet captions
    //     in the TweetBot, case-insensitively.
    // R - String: the most frequent word in lowercase
    // P - none
    public String getMostFrequentWord() {
        Map<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < bot.numTweets(); i++) {
            Tweet tw = bot.nextTweet();
            Scanner wordScan = new Scanner(tw.getCaption());
            while (wordScan.hasNext()) {
                String word = wordScan.next().toLowerCase();
                if (wordCount.containsKey(word)) {
                    wordCount.put(word, wordCount.get(word) + 1);
                } else {
                    wordCount.put(word, 1);
                }
            }
        }
        String mostFrequent = "";
        int highestCount = -1;
        for (String word : wordCount.keySet()) {
            if (wordCount.get(word) > highestCount) {
                highestCount = wordCount.get(word);
                mostFrequent = word;
            }
        }
        bot.reset();
        return mostFrequent;
    }

    // B - Returns the most popular tweet in the TweetBot, defined as the tweet
    //     with the highest combined total of likes and retweets.
    // R - Tweet: the most popular tweet
    // P - none
    public Tweet getMostPopularTweet() {
        Tweet mostPop = null;
        int topTrending = -1;
        for (int i = 0; i < bot.numTweets(); i++) {
            Tweet tw = bot.nextTweet();
            int tTotal = tw.getLikes() + tw.getRetweets();
            if (tTotal > topTrending) {
                topTrending = tTotal;
                mostPop = tw;
            }
        }
        bot.reset();
        return mostPop;
    }
}
