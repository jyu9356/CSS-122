// Jeffrey Yu
// 05/24/2026
// CSE 122 
// P3: Program Linting
// TA: Colin Lim

// Represents a check that flags lines containing a blank println statement.
// Implements the Check interface.

import java.util.*;

// TODO: Your Code Here

public class BlankPrintlnCheck implements Check {

    // B - Checks if the given line contains a blank println statement
    // E - none
    // R - an Error with code 3 if the line contains System.out.println(""), null otherwise
    // P - line, the line of code to check; lineNumber, the line number of the given line
    public Error lint(String line, int lineNumber) {
        if (line.contains("System.out.println(\"\")")) {
            return new Error(3, lineNumber, "Blank println statement!");
        }
        return null;
    }
}
