// Jeffrey Yu
// 05/24/2026
// CSE 122 
// P3: Program Linting
// TA: Colin Lim

// Represents a linter that runs a set of checks on a Java source file
// line by line and collects all errors found.

import java.util.*;
import java.io.*;

public class Linter {
    // TODO: Your code here

    
    private List<Check> checkList;

    // B - Constructs a Linter with the given list of checks to run
    // E - none
    // R - none
    // P - checkList, the list of checks to run on each line
    public Linter(List<Check> checkList) {
        this.checkList = checkList;
    }
    
    // B - Lints the given file line by line by running all checks on each line
    // E - throws FileNotFoundException if the given file cannot be found
    // R - a list of all Errors found in the file
    // P - fileName, the name of the file to lint
    public List<Error> lint(String fileName) throws FileNotFoundException {
        List<Error> errors = new ArrayList<>();

        Scanner fScan = new Scanner(new File(fileName));

        int lineNum = 1;
        while (fScan.hasNextLine()) {
            String line = fScan.nextLine();
            for (Check check : checkList) {
                Error error = check.lint(line, lineNum);
                if (error != null) {
                    errors.add(error);
                }
            }
            lineNum++;
        }
        return errors;
    }
}                   
