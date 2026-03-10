package com.airtribe.learntrack.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Enrollment entity class representing a student's enrollment in a course.
 * Links students to courses with enrollment date and status tracking.
 */
public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;

    // Default constructor
    public Enrollment() {
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    // Parameterized constructor with all fields
    public Enrollment(int id, int studentId, int courseId, LocalDate enrollmentDate, EnrollmentStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    // Constructor with default enrollment date and status
    public Enrollment(int id, int studentId, int courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    // Constructor with enrollment date but default status
    public Enrollment(int id, int studentId, int courseId, LocalDate enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
        this.status = EnrollmentStatus.ACTIVE;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    /**
     * Returns formatted enrollment date string.
     */
    public String getFormattedEnrollmentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return enrollmentDate.format(formatter);
    }

    /**
     * Marks the enrollment as completed.
     */
    public void markAsCompleted() {
        this.status = EnrollmentStatus.COMPLETED;
    }

    /**
     * Marks the enrollment as cancelled.
     */
    public void markAsCancelled() {
        this.status = EnrollmentStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "Enrollment{id=" + id + 
               ", studentId=" + studentId +
               ", courseId=" + courseId +
               ", enrollmentDate=" + getFormattedEnrollmentDate() +
               ", status=" + status + "}";
    }
}
