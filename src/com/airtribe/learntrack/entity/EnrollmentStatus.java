package com.airtribe.learntrack.entity;

/**
 * Enum representing the status of an enrollment.
 * Using enum for type safety and cleaner code.
 */
public enum EnrollmentStatus {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    EnrollmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
