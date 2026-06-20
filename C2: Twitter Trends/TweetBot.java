// Jeffrey Yu
// 05/18/2026
// CSE 122 
// C2: TwitterTrends
// TA: Colin Lim

// This class manages a collection of Tweet objects, simulating a social media feed.
// It tracks which tweets have been viewed and which have not, and allows
// adding, removing, and resetting the state of the feed.

import java.util.*;
import java.io.*;

public class TweetBot {
    // TODO: Your Code Here
    private List<Tweet> unviewed;
    private List<Tweet> viewed;

    // B - Constructs a new TweetBot with the given list of tweets, all starting as unviewed.
    // E - IllegalArgumentException if the given list has fewer than 1 tweet
    // P - tweets: the list of Tweet objects to initialize the TweetBot with
    public TweetBot(List<Tweet> tweets) {
        if(tweets.isEmpty()) {
            throw new IllegalArgumentException("Empty tweets list");   
        }
        unviewed = new ArrayList<>();
        viewed = new ArrayList<>();
        for(Tweet t : tweets) {
            unviewed.add(t);
        }
    }

    // B - Returns the total number of tweets in this TweetBot,
    //     including both viewed and unviewed tweets.
    // R - int: total number of tweets
    // P - none
    public int numTweets() {
        return unviewed.size() + viewed.size();
    }

    // B - Adds the given tweet to the end of the unviewed tweets collection.
    // P - tw: the Tweet to add
    public void addTweet(Tweet tw) {
        unviewed.add(tw);
    }

    // B - Views the next unviewed tweet, moving it to the viewed collection
    //     and returning it.
    // E - IllegalStateException if there are no more unviewed tweets
    // R - Tweet: the next unviewed tweet
    // P - none
    public Tweet nextTweet() {
        if (unviewed.isEmpty()) {
            throw new IllegalStateException("No more unviewed tweets");
        }
        Tweet next = unviewed.remove(0);
        viewed.add(next);
        return next;
    }

    // B - Removes the given tweet from the viewed collection.
    // E - IllegalArgumentException if the given tweet has not been viewed
    // P - tw: the Tweet to remove
    public void removeTweet(Tweet tw) {
        if (!viewed.contains(tw)) {
            throw new IllegalArgumentException("Tweet has not been viewed");
        }
        viewed.remove(tw);
    }

    // B - Restores the TweetBot to its original state by moving all viewed tweets
    //     back to the front of the unviewed collection in their original order.
    // P - none
    public void reset() {
        for (int i = viewed.size() - 1; i >= 0; i--) {
            unviewed.add(0, viewed.get(i));
        }
        viewed.clear();
    }
}   
