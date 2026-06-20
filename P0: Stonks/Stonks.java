// Jeffrey Yu
// 04/11/2026
// CSE 122
// TA: Colin Lim
// In this class, I implement a stock portfolio simulator that allows users to 
// buy and sell shares of stocks loaded from a file. Users can view their
// portfolio, save it to a file, and see their total portfolio value when they quit.

import java.util.*;
import java.io.*;

public class Stonks {
    public static void main(String[] args) throws FileNotFoundException {
        // TODO: write main method here
        Scanner console = new Scanner(System.in);
        System.out.print("Enter stocks file name: ");
        String fileName = console.next();
        Scanner fileScan = new Scanner(new File(fileName));

        int numStocks = Integer.parseInt(fileScan.nextLine());
        String[] stocks = new String[numStocks];
        double[] prices = new double[numStocks];
        double[] portfolio = new double[numStocks];

        populate(fileScan, stocks, prices);

        System.out.println("Welcome to the CSE 122 Stocks Simulator!");
        System.out.println("There are " + numStocks + " stocks on the market:");
        for(int i = 0; i < numStocks; i++) {
            System.out.println(stocks[i] + ": " + prices[i]);
        }

        System.out.println();
        String choice = "";

        while(!choice.equalsIgnoreCase("Q")) {
            System.out.println("Menu: (B)uy, (Se)ll, (D)isplay, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");

            choice = console.next();

            if(choice.equalsIgnoreCase("B")) {
                buy(console, stocks, prices, portfolio);
            } else if(choice.equalsIgnoreCase("Se")) {
                sell(console, stocks, portfolio);
            } else if(choice.equalsIgnoreCase("D")) {
                System.out.println();
                display(stocks, portfolio);
            } else if(choice.equalsIgnoreCase("S")) {
                save(console, stocks, portfolio);
            } else if(!choice.equalsIgnoreCase("Q")) {
                System.out.println("Invalid choice: " + choice);
                System.out.println("Please try again");
            }
        }

        System.out.println();
        double value = 0.0;
        for(int i = 0; i < numStocks; i++) {
            value += prices[i]* portfolio[i];
        }
        System.out.println("Your portfolio is currently valued at: $" + value);       
    }

    // TODO: write your methods here

    //B: Reads ticker and price info from a file and populates stock[]/prices[] with data
    //E: None
    //R: None
    //P: fileScan - scanner for reading the stock data in file
    //            stocks - array to store stock tickers
    //            prices - array to store stock prices
    public static void populate(Scanner fileScan, String[] stocks, double[] prices) {
        fileScan.nextLine();
        int counter = 0;
        while(fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner tokenScan = new Scanner(line);
            stocks[counter] = tokenScan.next();
            prices[counter] = Double.parseDouble(tokenScan.next());
            counter++;
        }
    }

    //B: Prompts the user for a stock ticker and a budget, then calculates and adds
    //   the number of shares they can buy to their portfolio given the budget.
    //   If the budget is below $5, nothing is done and an error message is outputted 
    //E: None
    //R: None
    //P: console - Scanner for reading user input
    //            stocks - array to store stock tickers
    //            prices - array to store stock prices
    //            portfolio - array containing shares owned 
    public static void buy(Scanner console, String[] stocks, double[] prices, double[] portfolio) {
        System.out.print("Enter the stock ticker: ");
        String ticker = console.next();

        int index = findStockIndex(stocks, ticker);
        
        System.out.print("Enter your budget: ");
        double budget = console.nextDouble();

        if(budget < 5.0) {
            System.out.println("Budget must be at least $5");
        } else { 
            double shares = budget/prices[index];
            portfolio[index] += shares;
            System.out.println("You successfully bought " + ticker + ".");
        }
        System.out.println();
    }

    //B: Prompts user for a stock ticker and # of shares to sell.
    //   Removes shares from portfolio if user owns enough, otherwise prints error message
    //E: None
    //R: None
    //P: console - Scanner for reading user input
    //            stocks - array to store stock tickers
    //            portfolio - array containing shares owned 
    public static void sell(Scanner console, String[] stocks, double[] portfolio) {
        System.out.print("Enter the stock ticker: ");
        String ticker = console.next();

        int index = findStockIndex(stocks, ticker);

        System.out.print("Enter the number of shares to sell: ");
        double shares = console.nextDouble();

        if(shares > portfolio[index]) {
            System.out.println("You do not have enough shares of " + ticker + " to sell " + shares + " shares" + ".");
        } else {
            portfolio[index] -= shares;
            System.out.println("You successfully sold " + shares + " shares of " + ticker + ".");
        }
        System.out.println();
    }

    //B: Prints all stocks the user currently has with their shares quantities.
    //   Prints blank line if the user owns no stocks.
    //E: None
    //R: None
    //P: stocks - array to store stock tickers
    //            portfolio - array containing shares owned 
    public static void display(String[] stocks, double[] portfolio) {
        System.out.println("Portfolio:");
        boolean hasStocks = false;
        for(int i = 0; i < stocks.length; i++){
            if(portfolio[i] > 0){
                System.out.println(stocks[i] + " " + portfolio[i]);
                hasStocks = true;
            }
        }
        if(!hasStocks) {
            System.out.println();
        }
        System.out.println();
    }

    //B: Prompts the user for a file name and saves their current portfolio
    //   to that file, with each owned stock and its quantity 
    //E: None in body
    //R: None
    //P: console - Scanner for reading user input
    //            stocks - array to store stock tickers
    //            portfolio - array containing shares owned 
    public static void save(Scanner console, String[] stocks, double[] portfolio) throws FileNotFoundException {
        System.out.print("Enter new portfolio file name: ");
        String fileName = console.next();
        PrintStream outFile = new PrintStream(new File(fileName));

        for(int i = 0; i < stocks.length; i++) {
            if(portfolio[i] > 0) {
                outFile.println(stocks[i]+ " " + portfolio[i]);
            }
        }
        System.out.println();
    }
    
    //B: Helper method used in "buy" and "sell" methods
    //   Searches through stocks array to find index of a given ticker.
    //E: None
    //R: int index - index of the ticker in the stocks array
    //P: stockNames - array of stock tickers
    //   ticker - target stock ticker to search for
    public static int findStockIndex(String[] stockNames, String ticker) {
        int index = 0;
        for(int i = 0; i < stockNames.length; i++) {
            if(stockNames[i].equals(ticker)) {
                index = i; 
            }
        }
        return index;
    }

}
