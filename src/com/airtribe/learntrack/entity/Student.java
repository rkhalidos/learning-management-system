package com.airtribe.learntrack.entity;

/**
 * Student entity class that extends Person.
 * Demonstrates inheritance, constructor overloading, and method overriding.
 */
public class Student extends Person {
    private String batch;
    private boolean active;

    // Default constructor
    public Student() {
        super();
        this.active = true;
    }

    // Parameterized constructor with all fields
    public Student(int id, String firstName, String lastName, String email, String batch, boolean active) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = active;
    }

    // Constructor without email (demonstrating constructor overloading)
    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName);
        this.batch = batch;
        this.active = true;
    }

    // Constructor with email but default active status
    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = true;
    }

    // Getters and Setters
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Overridden method demonstrating polymorphism.
     * Returns display name with batch information.
     */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [Batch: " + batch + "]";
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() + 
               ", name='" + getFirstName() + " " + getLastName() + "'" +
               ", email='" + getEmail() + "'" +
               ", batch='" + batch + "'" +
               ", active=" + active + "}";
    }
}
