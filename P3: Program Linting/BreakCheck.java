// Jeffrey Yu
// 05/24/2026
// CSE 122 
// P3: Program Linting
// TA: Colin Lim

// Represents a check that flags lines containing the break keyword
// outside of a single line comment. Implements the Check interface.

import java.util.*;

// TODO: Your Code Here

public class BreakCheck implements Check {

    // B - Checks if the given line contains the break keyword outside of a single line comment
    // E - none
    // R - an Error with code 2 if the line contains break outside of a // comment, null otherwise
    // P - line, the line of code to check; lineNumber, the line number of the given line
    public Error lint(String line, int lineNumber) {
        if (line.contains("break")) {
            if (line.contains("//") && line.indexOf("//") < line.indexOf("break")) {
                return null;
            }
            return new Error(2, lineNumber, "Line contains break statement!");
        }
    return null;
    }
}
