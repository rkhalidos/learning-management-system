package com.airtribe.learntrack.exception;

/**
 * Custom exception thrown when an entity (Student, Course, Enrollment) is not found.
 * Demonstrates custom exception handling in Java.
 */
public class EntityNotFoundException extends Exception {
    
    private final String entityType;
    private final int entityId;

    // Constructor with message only
    public EntityNotFoundException(String message) {
        super(message);
        this.entityType = "Unknown";
        this.entityId = -1;
    }

    // Constructor with entity type and ID
    public EntityNotFoundException(String entityType, int entityId) {
        super(entityType + " with ID " + entityId + " not found.");
        this.entityType = entityType;
        this.entityId = entityId;
    }

    // Constructor with message and cause
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.entityType = "Unknown";
        this.entityId = -1;
    }

    public String getEntityType() {
        return entityType;
    }

    public int getEntityId() {
        return entityId;
    }
}
