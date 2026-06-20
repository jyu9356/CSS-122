//Jeffrey Yu
//04/25/2026
//CSE 122 
//P1: Music Playlist
//TA: Colin Lim

//This class implements a music playlist manager that allows users to add songs as queue,
//play songs, and keep track of their listening history. The playlist is managed via a queue
//and the history is managed using a stack. Users interact through a text-based menu.

import java.util.*;
import java.io.*;

public class MusicPlaylist {
    public static void main(String[] args) {
        // TODO: Your Code Here
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the CSE 122 Music Playlist!");
        
        Queue<String> playlist = new LinkedList<>();
        Stack<String> history = new Stack<>();
        
        String choice = "";
        while(!choice.equalsIgnoreCase("Q")) {
            System.out.println("Menu: (A)dd song, (P)lay song, (Pr)int history, " + 
                           "(V)iew playlist, (C)lear history, (D)elete from history, (Q)uit");

            System.out.print("Enter your choice: ");
            choice = console.nextLine();

            if(choice.equalsIgnoreCase("A")) {
                addSong(console, playlist);
            } else if (choice.equalsIgnoreCase("P")) {
                playSong(playlist, history);
            } else if (choice.equalsIgnoreCase("Pr")) {
                printHistory(history);
            } else if (choice.equalsIgnoreCase("V")) {
                viewPlaylist(playlist);
            } else if (choice.equalsIgnoreCase("C")) {
                clearHistory(history);
            } else if (choice.equalsIgnoreCase("D")) {
                deleteHistory(console, history);
            } else if (choice.equalsIgnoreCase("Q")) {
                
            } else {
                System.out.println("Invalid choice: " + choice);
                System.out.println("Please try again");
                System.out.println();
            }
        }
        System.out.println();
    }

    //B: Gets user input for a song name and adds it to the back of the playlist queue.
    //   Prints a message confirming the song has been successfully added.
    //E: None
    //R: None
    //P: console - Scanner to read user input
    //   playlist - Queue representing the current playlist
    public static void addSong(Scanner console, Queue<String> playlist) {
        System.out.print("Enter song name: ");
        String song = console.nextLine();
        playlist.add(song);
        System.out.println("Successfully added " + song);
        System.out.println();
    }

    //B: Plays the next song in the playlist by removing it from the front of the
    //   the playlist queue, printing its name, and adding it to the history stack.
    //E: Throws IllegalStateException if the playlist is empty
    //R: None
    //P: playlist - Queue representing the current playlist
    //   history - Stack representing the listening history
    public static void playSong(Queue<String> playlist, Stack<String> history) {
        if (playlist.isEmpty()) {
            throw new IllegalStateException("Playlist is empty!");
        }
        String removedSong = playlist.remove();
        System.out.println("Playing song: " + removedSong);
        history.push(removedSong);
        System.out.println();
    }

    //B: Prints all songs in the history in reverse chronological order
    //   The most recently played songs are first. The history stack is
    //   restored to its original state after printing.
    //E: Throws IllegalStateException if the history is empty
    //R: None
    //P: history - Stack representing the listening history
    public static void printHistory(Stack<String> history) {
        if(history.isEmpty()) {
            throw new IllegalStateException("History is empty!");
        }
        Stack<String> aux = new Stack<>();
        while(!history.isEmpty()) {
            String song = history.pop();
            aux.push(song);
            System.out.println("    " + song);
        }
        sToS(aux, history);
        System.out.println();
    }
    
    //B: Prints all songs currently in the playlist in order. The first
    //   to be played are at the top, whereas the most recently are at the bottom.
    //   The playlist queue is restored to its original state after printing.
    //E: Throws IllegalStateException if the playlist is empty
    //R: None
    //P: playlist - Queue representing the current playlist
    public static void viewPlaylist(Queue<String> playlist) {
        if(playlist.isEmpty()) {
            throw new IllegalStateException("Playlist is empty!");
        }
        int n = playlist.size();
        for(int i = 0; i < n; i++) {
            String song = playlist.remove();
            System.out.println("    " + song);
            playlist.add(song);
        }
        System.out.println();
    }
    
    //B: Clears all songs from the listening history without creating a new object.
    //   Does not affect the contents of the playlist.
    //E: None
    //R: None
    //P: history - Stack representing the listening history
    public static void clearHistory(Stack<String> history) {
        while(!history.isEmpty()) {
            history.pop();
        }
        System.out.println();
    }

    //B: Prompts the user for a number and deletes that many songs from the history.
    //   A positive number deletes from the most recently played songs; a negative
    //   number deletes from the chronologically oldest songs. Does nothing if 0.
    //E: Throws IllegalArgumentException if Math.abs(input) exceeds the history size
    //R: None
    //P: console - Scanner to read user input
    //   history - Stack representing the listening history
    public static void deleteHistory(Scanner console, Stack<String> history) {

        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        int numDelete = Integer.parseInt(console.nextLine());


        if(history.size() < Math.abs(numDelete)) {
            throw new IllegalArgumentException("History size is less than user input!");
        }
        if(numDelete > 0) {
            for(int i = 0; i < numDelete; i++) {
                history.pop();
            }
        } else if (numDelete < 0) {
            Stack<String> aux = new Stack<>();
            sToS(history, aux);
            for (int i = 0; i < Math.abs(numDelete); i++) {
                aux.pop();
            }
            sToS(aux, history);
        }
        System.out.println();
    }

    //B: Helper method that transfers all elements from one Stack to another,
    //   reversing their order. The source stack is empty after this.
    //E: None
    //R: None
    //P: sFrom - Stack to transfer elements from
    //   sInto - Stack to transfer elements into
    public static void sToS(Stack<String> sFrom, Stack<String> sInto) {
        while(!sFrom.isEmpty()) {
            sInto.push(sFrom.pop());
        }
    }
}
