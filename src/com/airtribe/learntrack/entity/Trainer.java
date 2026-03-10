package com.airtribe.learntrack.entity;

/**
 * Trainer entity class that extends Person.
 * Demonstrates inheritance and method overriding.
 * Optional class to show multiple inheritance examples.
 */
public class Trainer extends Person {
    private String specialization;
    private int yearsOfExperience;
    private boolean active;

    // Default constructor
    public Trainer() {
        super();
        this.active = true;
    }

    // Parameterized constructor with all fields
    public Trainer(int id, String firstName, String lastName, String email, 
                   String specialization, int yearsOfExperience) {
        super(id, firstName, lastName, email);
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.active = true;
    }

    // Constructor without email
    public Trainer(int id, String firstName, String lastName, String specialization) {
        super(id, firstName, lastName);
        this.specialization = specialization;
        this.yearsOfExperience = 0;
        this.active = true;
    }

    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Overridden method demonstrating polymorphism.
     * Returns display name with specialization information.
     */
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [Trainer - " + specialization + "]";
    }

    @Override
    public String toString() {
        return "Trainer{id=" + getId() + 
               ", name='" + getFirstName() + " " + getLastName() + "'" +
               ", email='" + getEmail() + "'" +
               ", specialization='" + specialization + "'" +
               ", yearsOfExperience=" + yearsOfExperience +
               ", active=" + active + "}";
    }
}
