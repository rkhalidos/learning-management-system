package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/**
 * Utility class for validating user input.
 * Provides methods to validate various types of input data.
 */
public class InputValidator {

    // Email regex pattern for basic validation
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Private constructor to prevent instantiation
    private InputValidator() {
        // This class should not be instantiated
    }

    /**
     * Validates that a string is not null or empty.
     * @param value the string to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if the value is null or empty
     */
    public static void validateNotEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, value, fieldName + " cannot be empty.");
        }
    }

    /**
     * Validates that a string has minimum length.
     * @param value the string to validate
     * @param fieldName the name of the field
     * @param minLength the minimum required length
     * @throws InvalidInputException if the value is too short
     */
    public static void validateMinLength(String value, String fieldName, int minLength) throws InvalidInputException {
        validateNotEmpty(value, fieldName);
        if (value.trim().length() < minLength) {
            throw new InvalidInputException(fieldName, value, 
                fieldName + " must be at least " + minLength + " characters long.");
        }
    }

    /**
     * Validates that a string has maximum length.
     * @param value the string to validate
     * @param fieldName the name of the field
     * @param maxLength the maximum allowed length
     * @throws InvalidInputException if the value is too long
     */
    public static void validateMaxLength(String value, String fieldName, int maxLength) throws InvalidInputException {
        if (value != null && value.length() > maxLength) {
            throw new InvalidInputException(fieldName, value, 
                fieldName + " cannot exceed " + maxLength + " characters.");
        }
    }

    /**
     * Validates email format using basic regex.
     * @param email the email to validate
     * @throws InvalidInputException if the email format is invalid
     */
    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) {
            return; // Email is optional, empty is allowed
        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new InvalidInputException("email", email, "Invalid email format.");
        }
    }

    /**
     * Validates that an integer is positive.
     * @param value the integer to validate
     * @param fieldName the name of the field
     * @throws InvalidInputException if the value is not positive
     */
    public static void validatePositiveInteger(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName, String.valueOf(value), 
                fieldName + " must be a positive number.");
        }
    }

    /**
     * Validates that an integer is non-negative.
     * @param value the integer to validate
     * @param fieldName the name of the field
     * @throws InvalidInputException if the value is negative
     */
    public static void validateNonNegativeInteger(int value, String fieldName) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException(fieldName, String.valueOf(value), 
                fieldName + " cannot be negative.");
        }
    }

    /**
     * Validates that an integer is within a specified range.
     * @param value the integer to validate
     * @param fieldName the name of the field
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @throws InvalidInputException if the value is out of range
     */
    public static void validateIntegerRange(int value, String fieldName, int min, int max) throws InvalidInputException {
        if (value < min || value > max) {
            throw new InvalidInputException(fieldName, String.valueOf(value), 
                fieldName + " must be between " + min + " and " + max + ".");
        }
    }

    /**
     * Parses a string to integer with validation.
     * @param value the string to parse
     * @param fieldName the name of the field
     * @return the parsed integer
     * @throws InvalidInputException if the value cannot be parsed as integer
     */
    public static int parseInteger(String value, String fieldName) throws InvalidInputException {
        validateNotEmpty(value, fieldName);
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName, value, 
                fieldName + " must be a valid number.");
        }
    }

    /**
     * Validates menu choice within given range.
     * @param choice the user's choice
     * @param maxOption the maximum valid option
     * @throws InvalidInputException if the choice is invalid
     */
    public static void validateMenuChoice(int choice, int maxOption) throws InvalidInputException {
        if (choice < 0 || choice > maxOption) {
            throw new InvalidInputException("menu choice", String.valueOf(choice), 
                "Please enter a valid option (0-" + maxOption + ").");
        }
    }
}
