// Jeffrey Yu
// 05/03/2026
// CSE 122 
// P2: Absurdle
// TA: Colin Lim

// This class implements Absurdle, a variant of Wordle that cheats by never
// committing to a single secret word. Instead, it maintains a set of possible
// words and after each guess chooses the pattern that keeps the most words
// alive in order to make the game as difficult as possible for the player.

import java.util.*;
import java.io.*;

public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    // [[ ALL OF MAIN PROVIDED ]]
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = prepDictionary(contents, wordLength);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = recordGuess(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }

    // [[ PROVIDED ]]
    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }

    // [[ PROVIDED ]]
    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }

    // [[ PROVIDED ]]
    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }

    // B: Filters a dictionary list down to only words matching the given length.
    // E: Throws IllegalArgumentException if wordLength is less than 1
    // R: Returns a Set<String> of words matching the given length
    // P: List<String> contents - the full dictionary word list
    //    int wordLength - the desired word length to filter by
    public static Set<String> prepDictionary(List<String> contents, int wordLength) {
        if(wordLength < 1) {
            throw new IllegalArgumentException("Invalid word length!");
        }
        Set<String> matchLength = new HashSet<>();
        for(String word : contents) { 
            if(word.length() == wordLength) {
                matchLength.add(word);
            }
        }
        return matchLength;
    }

    // B: Processes the player's guess by grouping remaining words by pattern,
    //    choosing the pattern with the most words, updating the word set,
    //    and returning the best pattern.
    // E: Throws IllegalArgumentException if words is empty or guess length
    //    does not match wordLength
    // R: Returns a String representing the best pattern for the given guess
    // P: String guess - the player's guessed word
    //    Set<String> words - the current set of possible target words
    //    int wordLength - the expected length of the guess
    public static String recordGuess(String guess, Set<String> words, int wordLength) {
        if(words.size() == 0 || guess.length() != wordLength) {
            throw new IllegalArgumentException("No words available or invalid word length!");
        }
        
        Map<String, Set<String>> wordGroups =  createPatternGroups(guess, words);

        String bestPattern = "";
        for(String pattern : wordGroups.keySet()) {
            if(bestPattern.length() == 0 || 
               wordGroups.get(pattern).size() > wordGroups.get(bestPattern).size()) {
                
                bestPattern = pattern;
            }
        }

        words.clear();
        words.addAll(wordGroups.get(bestPattern));

        return bestPattern;
    }

    // B: Groups all remaining words by the pattern they produce against the guess.
    // E: None
    // R: Returns a TreeMap<String, Set<String>> mapping each pattern to the
    //    set of words that produce it
    // P: String guess - the player's guessed word
    //    Set<String> words - the current set of possible target words
    public static Map<String, Set<String>> createPatternGroups(String guess, Set<String> words) {
        Map<String, Set<String>> wordGroups = new TreeMap<>();
        for(String word: words) {
            String pattern = patternFor(word, guess);
            if(!wordGroups.containsKey(pattern)) {
                wordGroups.put(pattern, new HashSet<>());
            } 
            wordGroups.get(pattern).add(word);
            
        }
        return wordGroups;
    }

    // B: Generates a Wordle pattern by comparing a guess against a target word,
    //    assigning green, yellow, and gray tiles accordingly.
    // E: None
    // R: Returns a String of colored tiles representing the pattern
    // P: String word - the target word to compare against
    //    String guess - the player's guessed word
    public static String patternFor(String word, String guess) {
        List<String> letters = guessChars(guess);
        Map<Character, Integer> numChars = countChars(word);

        //Green
        for (int i = 0; i < word.length(); i++) {
            if (guess.charAt(i) == word.charAt(i)) {
                letters.set(i, GREEN);
                numChars.put(word.charAt(i), numChars.get(word.charAt(i)) - 1);
            }
        }

        //Yellows
        for (int i = 0; i < guess.length(); i++) {
            if (!letters.get(i).equals(GREEN)) {
                char c = guess.charAt(i);
                if (numChars.containsKey(c) && numChars.get(c) > 0) {
                    letters.set(i, YELLOW);
                    numChars.put(c, numChars.get(c) - 1);
                }
            }
        }

        //Grays
        for (int i = 0; i < letters.size(); i++) {
            if (!letters.get(i).equals(GREEN) && !letters.get(i).equals(YELLOW)) {
                letters.set(i, GRAY);
            }
        } 

        return listToString(letters);
    }

    // B: Converts a guess string into a List<String> where each element
    //    is a single character from the guess.
    // E: None
    // R: Returns a List<String> of individual characters from the guess
    // P: String guess - the word to convert into a list of characters
    public static List<String> guessChars (String guess) {
        List<String> characters = new ArrayList<>();
        for(int i = 0; i < guess.length(); i++) {
            characters.add("" + guess.charAt(i));
        }
        return characters;
    }

    // B: Counts the frequency of each character in the given word.
    // E: None
    // R: Returns a Map<Character, Integer> mapping each character to its count
    // P: String word - the word to count characters from
    public static Map<Character, Integer> countChars (String word) {
        Map<Character, Integer> charMap = new TreeMap<>();
        for(int i = 0; i < word.length(); i++) {
            char let = word.charAt(i);
            if(!charMap.containsKey(let)) {
                charMap.put(let, 1);
            } else {
                charMap.put(let, charMap.get(let) + 1);
            }
        }
        return charMap;
    }

    // B: Concatenates all elements of a List<String> into a single String.
    // E: None
    // R: Returns a single String of all elements joined together
    // P: List<String> letters - the list of strings to concatenate
    public static String listToString(List<String> letters) {
        String s = "";
        for(int i = 0; i < letters.size(); i++) {
            s += letters.get(i);
        }
        return s;
    } 
}
