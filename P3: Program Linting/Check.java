import java.util.*;

// A common interface for linters that can check a single line of code
// Every Check will check for its own type of error on a single line of code.
public interface Check {
	// Checks for this Check's error condition on this line with this line number.
	// Returns an Error object if an error exists on this line; returns null otherwise.
	public Error lint(String line, int lineNumber);
}

