package com.airtribe.learntrack.util;

/**
 * Utility class for generating unique IDs for entities.
 * Demonstrates the use of static fields and methods.
 * All IDs are auto-incremented to ensure uniqueness.
 */
public class IdGenerator {
    
    // Static counters for each entity type
    private static int studentIdCounter = 0;
    private static int courseIdCounter = 0;
    private static int enrollmentIdCounter = 0;
    private static int trainerIdCounter = 0;

    // Private constructor to prevent instantiation (utility class pattern)
    private IdGenerator() {
        // This class should not be instantiated
    }

    /**
     * Generates and returns the next unique Student ID.
     * @return the next Student ID
     */
    public static int getNextStudentId() {
        return ++studentIdCounter;
    }

    /**
     * Generates and returns the next unique Course ID.
     * @return the next Course ID
     */
    public static int getNextCourseId() {
        return ++courseIdCounter;
    }

    /**
     * Generates and returns the next unique Enrollment ID.
     * @return the next Enrollment ID
     */
    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }

    /**
     * Generates and returns the next unique Trainer ID.
     * @return the next Trainer ID
     */
    public static int getNextTrainerId() {
        return ++trainerIdCounter;
    }

    /**
     * Returns the current Student ID counter value (for display/debugging).
     * @return current Student ID counter
     */
    public static int getCurrentStudentIdCounter() {
        return studentIdCounter;
    }

    /**
     * Returns the current Course ID counter value (for display/debugging).
     * @return current Course ID counter
     */
    public static int getCurrentCourseIdCounter() {
        return courseIdCounter;
    }

    /**
     * Returns the current Enrollment ID counter value (for display/debugging).
     * @return current Enrollment ID counter
     */
    public static int getCurrentEnrollmentIdCounter() {
        return enrollmentIdCounter;
    }

    /**
     * Resets all ID counters. Useful for testing purposes.
     */
    public static void resetAllCounters() {
        studentIdCounter = 0;
        courseIdCounter = 0;
        enrollmentIdCounter = 0;
        trainerIdCounter = 0;
    }
}
