// Jeffrey Yu 
// 04/19/2026
// CSE 122 
// C1: Todo List Manager
// TA: Colin Lim
//
// This program implements a TODO list manager that allows users to add.
// remove, save, and load TODO items. The program continuously prompts
// the user for actions until they choose to quit. 
import java.util.*;
import java.io.*;

public class TodoListManager {
    /**
     * Flag name should be one of the following:
     * MARK_RANGE
     * PRIORITY_LEVELS
     * SORT_TODOS
     */
    public static final boolean MARK_RANGE = false;

    public static void main(String[] args) throws FileNotFoundException {
        // TODO: Your Code Here       
        System.out.println("Welcome to your TODO List Manager!");     
        Scanner console = new Scanner(System.in);
        String choice = "";
        List<String> todos = new ArrayList<>();

        while(!choice.equalsIgnoreCase("Q")) {
            if(MARK_RANGE == true) {
                System.out.println("Menu: (A)dd TODO, (M)ark multiple TODOs as done, " +
                                   "(L)oad TODOs, (S)ave TODOs, (Q)uit?");
            }
            else { 
                System.out.println("Menu: (A)dd TODO, (M)ark TODO as done, (L)oad TODOs, " + 
                                   "(S)ave TODOs, (Q)uit?");
            }
            System.out.print("Enter your choice: ");
            choice = console.nextLine();

            if(choice.equalsIgnoreCase("A")) {
                addItem(console, todos);
                printTodos(todos);
            } else if (choice.equalsIgnoreCase("M")){
                markItemAsDone(console, todos);
                printTodos(todos);
            } else if (choice.equalsIgnoreCase("L")){
                loadItems(console, todos);
                printTodos(todos);
            } else if (choice.equalsIgnoreCase("S")){
                saveItems(console, todos);
                printTodos(todos);
            } else if (choice.equalsIgnoreCase("Q")){
                //do nothing if "Q" to ensure next else block doesn't run
            } else {
                System.out.println("Invalid choice: " + choice);
                System.out.println("Please try again");
                printTodos(todos);
            }
        }
        printTodos(todos);
    }

    //B: Prints the current TODO list with numbered items and a header.
    //   If the list is empty, prints a message indicating nothing to do left.
    //E: None
    //R: Nothing
    //P: todos, the list of TODO items to print
    public static void printTodos(List<String> todos) {
        // TODO: Your Code Here
        System.out.println();
        System.out.println("Today's TODOs:");
        for(int i = 0; i < todos.size(); i++) {
            System.out.println("  " + (i+1) + ": " + todos.get(i));
        }
        if(todos.size() == 0) {
            System.out.println("  You have nothing to do yet today! Relax!");
        }
        System.out.println();
    }

    //B: Prompts the user for a new TODO item and adds it to the list.
    //   If the list is not empty, asks the user where to insert the item.
    //   If the user hits "enter" without a number, add to end of the list.
    //E: None
    //R: Nothing
    //P: console - Scanner for user input
    //   todos - the list of TODO items to modify
    public static void addItem(Scanner console, List<String> todos) {
        // TODO: Your Code Here
        System.out.print("What would you like to add? ");
        String item = console.nextLine();

        if(todos.size() == 0) {
            todos.add(item);
        }
        else {
            System.out.print("Where in the list should it be (1-" + (todos.size() + 1) + 
                             ")? (Enter for end): ");
                         
            String input = console.nextLine();
            int index = 0;
            if(!input.equals("")) {
                index = Integer.parseInt(input);
            }

            if(input.equals("")) {
                todos.add(item);
            } else { 
                todos.add(index - 1, item);
            }
        }
    }

    //B: Prompts the user for a TODO item number and removes it from 
    //   the list. If the list is empty, prints a message instead.
    //   If MARK_RANGE is true, prompts for a range of items to remove.
    //E: None
    //R: Nothing
    //P: console - Scanner for user input
    //   todos - the list of TODO items to modify
    public static void markItemAsDone(Scanner console, List<String> todos) {
        // TODO: Your Code Here
        if(todos.size() != 0) {
            if(MARK_RANGE == true) {
                System.out.print("What is the first item you completed " + 
                                 "(1-" + todos.size() + ")? ");
                int first = Integer.parseInt(console.nextLine());

                System.out.print("What is the last item you completed " + 
                                 "(1-" + todos.size() + ")? ");
                int last = Integer.parseInt(console.nextLine());              

                for(int i = last - 1; i >= first - 1; i--) {
                    todos.remove(i);
                }
            } else {
                System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
                int option = Integer.parseInt(console.nextLine());
                todos.remove(option - 1);
            }
            
        }
        else {
            System.out.println("All done! Nothing left to mark as done!");
        }
    }

    //B: Prompts the user for a file name and loads TODO items from
    //   that file, replacing any existing items in the list. 
    //   Each line in the file translate into a TODO item.
    //E: Throws FileNotFoundException if the file cannot be found
    //R: Nothing
    //P: console - Scanner for user input
    //   todos - the list of TODO items to replace with file contents
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        // TODO: Your Code Here
        todos.clear();
        System.out.print("File name? ");
        String fName = console.nextLine();

        Scanner fileScan = new Scanner(new File(fName));
        while(fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            todos.add(line);
        }
    }

    //B: Prompts the user for a file name and saves all TODO items
    //E: Throws FileNotFoundException if the file cannot be created
    //R: Nothing
    //P: console - Scanner for user input
    //   todos - the list of TODO items to save
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        // TODO: Your Code Here
        System.out.print("File name? ");
        String fName = console.nextLine();

        PrintStream fOut = new PrintStream(new File(fName));
        for(int i = 0; i < todos.size(); i++) {
            fOut.println(todos.get(i));
        }
    }
}
