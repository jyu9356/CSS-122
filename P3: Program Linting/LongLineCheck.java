// Jeffrey Yu
// 05/24/2026
// CSE 122 
// P3: Program Linting
// TA: Colin Lim

//Represents a check that flags lines over 100 characters in length 
//and implements the Check interface 

import java.util.*;

// TODO: Your Code Here

public class LongLineCheck implements Check {

    // B - Checks if the given line exceeds 100 characters in length
    // E - none
    // R - an Error with code 1 if the line exceeds 100 characters, null otherwise
    // P - line, the line of code to check; lineNumber, the line number of the given line
    public Error lint(String line, int lineNumber) {
        if(line.length() > 100) {
            return new Error(1, lineNumber, "Line is too long!");
        } 
        return null;
    }
}
