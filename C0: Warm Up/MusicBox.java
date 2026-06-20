// Jeffrey Yu
// 04/05/2026
// CSE 122
// TA: Colin Lim
// This program builds on the MusicBox program by adding the mostCommonNaturals
// method, which takes a song and returns the most frequently occurring natural note in each melody.

import java.util.*;

public class MusicBox {
    public static final String NOTES = "CDEFGAB";
    public static final String SHARP = "♯";
    public static final String FLAT = "♭";
    
    public static void main(String[] args) {
        // Restore the main method to its original state (no additional debugging prints)
        // when submitting the assignment!
        Scanner console = new Scanner(System.in);
        String[][] song = composeSong(console);
        System.out.println("Returned song 2D array:");
        for (int i = 0; i < song.length; i++) {
            for (int j = 0; j < song[0].length; j++) {
                System.out.print(song[i][j] + " "); 
            }
            System.out.println();
        }
    }

    // TODO: copy and paste the composeSong method and helper methods here
    
    //This method takes in a Scanner console, that allows for user input
    //Use: It prompts the user to create a full song by inputting notes to create melodies
    //It returns a String[][] representing the full song the user composed
    public static String[][] composeSong(Scanner console) {
        System.out.print("Enter the number of melodies: ");
        int numMel = Integer.parseInt(console.nextLine());
        System.out.print("Enter the length of each melody: ");
        int lenMel = Integer.parseInt(console.nextLine());
        System.out.println();

        String[][] songArr = new String[numMel][lenMel]; 

        for(int i = 0; i < numMel; i++){
            System.out.println("Composing melody #" + (i+1));
            for(int j = 0; j < lenMel; j++){
                songArr[i][j] = composeNote(console, j+1);
            }
            System.out.println();
        }
        return songArr;
    }

    //Prompts the user to enter 1 note and returns it
    //Returns the note the user entered as a string
    public static String composeNote(Scanner console, int noteNum){
        System.out.print("  Enter note #" + noteNum + ": ");
        return console.nextLine();
    }

    // TODO: choose ONE creative extension option to implement here
    
    //This finds the most common natural notein each melody 
    //It returns a String[] where each element is the most frequent natural note 
    //of the corresponding melody in song
    public static String[] mostCommonNaturals(String[][] song) {
        String[] mostNatural = new String[song.length];
        for(int i = 0; i < song.length; i++){
            int[] numNat = countNats(song[i]);
            int pos = mostNaturalsPos(numNat); 
            mostNatural[i] = "" + NOTES.charAt(pos);
        }
        return mostNatural;
    }

    //This finds the index of the highest count in the array (for a natural)
    //It returns the index of the largest value in nats
    public static int mostNaturalsPos(int[] nats){
        int largestNum = 0;
        int pos = 0;
        for(int i = 0; i < nats.length; i++) {
            if(nats[i] > largestNum){
                largestNum = nats[i];
                pos = i;
            }
        }
        return pos;
    }

    //Counts occurrences of each natural note in a melody
    //Returns an int[] of length 7 with the amounts of the naturals C,D,E,F,G,A,B respectively 
    public static int[] countNats(String[] melody){
        int[] numNat = new int[7];
        for(int i = 0; i < melody.length; i++){
            for(int j = 0; j < numNat.length; j++){
                if(melody[i].equals("" + NOTES.charAt(j))){
                    numNat[j]++;
                }
            }
        }
        return numNat;
    }
    
}
