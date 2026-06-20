// Jeffrey Yu
// 05/24/2026
// CSE 122 
// P3: Program Linting
// TA: Colin Lim


// Represents a linting error found in a Java source file,
// storing its line number, error code, and description message.
public class Error {
    private int code;
    private int lineNumber;
    private String message;

    // B - Constructs an Error with the given error code, line number, and message
    // E - none
    // R - none
    // P - code, the error code; lineNumber, the line number; message, the error description
    public Error(int code, int lineNumber, String message) {
        this.code = code;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    // B - Returns a string representation of this error with line number, code, and message
    // E - none
    // R - a formatted String representing this error
    // P - none
    public String toString() {
        return "(Line: " + lineNumber + ") has error code " + code + ": " + message;
    }

    // B - Returns the line number on which this error occurred
    // E - none
    // R - the line number of this error
    // P - none
    public int getLineNumber() {
        return lineNumber;
    }

    // B - Returns the error code of this error
    // E - none
    // R - the error code of this error
    // P - none
    public int getCode() {
        return code;
    }

    // B - Returns the description message of this error
    // E - none
    // R - the message of this error
    // P - none
    public String getMessage() {
        return message;
    }
}
