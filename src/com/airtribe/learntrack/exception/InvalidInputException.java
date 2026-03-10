package com.airtribe.learntrack.exception;

/**
 * Custom exception thrown when invalid input is provided by the user.
 * Used for input validation in the console application.
 */
public class InvalidInputException extends Exception {
    
    private final String fieldName;
    private final String invalidValue;

    // Constructor with message only
    public InvalidInputException(String message) {
        super(message);
        this.fieldName = "Unknown";
        this.invalidValue = "";
    }

    // Constructor with field name and invalid value
    public InvalidInputException(String fieldName, String invalidValue) {
        super("Invalid value '" + invalidValue + "' for field: " + fieldName);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    // Constructor with field name, invalid value, and custom message
    public InvalidInputException(String fieldName, String invalidValue, String customMessage) {
        super(customMessage);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    // Constructor with message and cause
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = "Unknown";
        this.invalidValue = "";
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}
