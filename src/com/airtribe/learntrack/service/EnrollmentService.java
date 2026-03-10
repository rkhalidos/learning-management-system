package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

import java.util.ArrayList;

/**
 * Service class for managing Enrollment entities.
 * Handles all business logic related to enrollments.
 * Uses ArrayList to store enrollment data in-memory.
 */
public class EnrollmentService {

    // ArrayList to store enrollments (in-memory storage)
    private ArrayList<Enrollment> enrollments;

    // References to other services for validation
    private StudentService studentService;
    private CourseService courseService;

    // Constructor
    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.enrollments = new ArrayList<>();
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Enrolls a student in a course.
     * @param studentId the student's ID
     * @param courseId the course's ID
     * @return the created Enrollment object
     * @throws EntityNotFoundException if student or course is not found
     * @throws InvalidInputException if student is already enrolled in the course
     */
    public Enrollment enrollStudent(int studentId, int courseId) 
            throws EntityNotFoundException, InvalidInputException {
        // Validate that student exists
        studentService.getStudentById(studentId);
        
        // Validate that course exists
        courseService.getCourseById(courseId);
        
        // Check if student is already enrolled in this course with ACTIVE status
        if (isStudentEnrolledInCourse(studentId, courseId)) {
            throw new InvalidInputException("enrollment", 
                "Student " + studentId + " - Course " + courseId,
                "Student is already enrolled in this course.");
        }

        // Generate unique ID and create enrollment
        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        
        // Add to list
        enrollments.add(enrollment);
        
        return enrollment;
    }

    /**
     * Retrieves an enrollment by ID.
     * @param enrollmentId the enrollment ID to search for
     * @return the Enrollment if found
     * @throws EntityNotFoundException if enrollment is not found
     */
    public Enrollment getEnrollmentById(int enrollmentId) throws EntityNotFoundException {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == enrollmentId) {
                return enrollment;
            }
        }
        throw new EntityNotFoundException("Enrollment", enrollmentId);
    }

    /**
     * Returns all enrollments in the system.
     * @return ArrayList of all enrollments
     */
    public ArrayList<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    /**
     * Returns enrollments for a specific student.
     * @param studentId the student's ID
     * @return ArrayList of enrollments for the student
     */
    public ArrayList<Enrollment> getEnrollmentsByStudentId(int studentId) {
        ArrayList<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                studentEnrollments.add(enrollment);
            }
        }
        return studentEnrollments;
    }

    /**
     * Returns active enrollments for a specific student.
     * @param studentId the student's ID
     * @return ArrayList of active enrollments for the student
     */
    public ArrayList<Enrollment> getActiveEnrollmentsByStudentId(int studentId) {
        ArrayList<Enrollment> activeEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && 
                enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                activeEnrollments.add(enrollment);
            }
        }
        return activeEnrollments;
    }

    /**
     * Returns enrollments for a specific course.
     * @param courseId the course's ID
     * @return ArrayList of enrollments for the course
     */
    public ArrayList<Enrollment> getEnrollmentsByCourseId(int courseId) {
        ArrayList<Enrollment> courseEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                courseEnrollments.add(enrollment);
            }
        }
        return courseEnrollments;
    }

    /**
     * Marks an enrollment as completed.
     * @param enrollmentId the enrollment ID
     * @return the updated Enrollment
     * @throws EntityNotFoundException if enrollment is not found
     * @throws InvalidInputException if enrollment is not in ACTIVE status
     */
    public Enrollment markAsCompleted(int enrollmentId) 
            throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = getEnrollmentById(enrollmentId);
        
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("enrollment status", 
                enrollment.getStatus().toString(),
                "Only ACTIVE enrollments can be marked as completed.");
        }
        
        enrollment.markAsCompleted();
        return enrollment;
    }

    /**
     * Marks an enrollment as cancelled.
     * @param enrollmentId the enrollment ID
     * @return the updated Enrollment
     * @throws EntityNotFoundException if enrollment is not found
     * @throws InvalidInputException if enrollment is not in ACTIVE status
     */
    public Enrollment cancelEnrollment(int enrollmentId) 
            throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = getEnrollmentById(enrollmentId);
        
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("enrollment status", 
                enrollment.getStatus().toString(),
                "Only ACTIVE enrollments can be cancelled.");
        }
        
        enrollment.markAsCancelled();
        return enrollment;
    }

    /**
     * Updates enrollment status.
     * @param enrollmentId the enrollment ID
     * @param newStatus the new status
     * @return the updated Enrollment
     * @throws EntityNotFoundException if enrollment is not found
     */
    public Enrollment updateEnrollmentStatus(int enrollmentId, EnrollmentStatus newStatus) 
            throws EntityNotFoundException {
        Enrollment enrollment = getEnrollmentById(enrollmentId);
        enrollment.setStatus(newStatus);
        return enrollment;
    }

    /**
     * Checks if a student is currently enrolled in a course (with ACTIVE status).
     * @param studentId the student's ID
     * @param courseId the course's ID
     * @return true if the student has an active enrollment in the course
     */
    public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId && 
                enrollment.getCourseId() == courseId &&
                enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns enrollments filtered by status.
     * @param status the status to filter by
     * @return ArrayList of enrollments with the specified status
     */
    public ArrayList<Enrollment> getEnrollmentsByStatus(EnrollmentStatus status) {
        ArrayList<Enrollment> filteredEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() == status) {
                filteredEnrollments.add(enrollment);
            }
        }
        return filteredEnrollments;
    }

    /**
     * Returns the total count of enrollments.
     * @return total number of enrollments
     */
    public int getTotalEnrollmentCount() {
        return enrollments.size();
    }

    /**
     * Returns the count of active enrollments.
     * @return number of active enrollments
     */
    public int getActiveEnrollmentCount() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the count of enrollments for a specific student.
     * @param studentId the student's ID
     * @return number of enrollments for the student
     */
    public int getEnrollmentCountByStudent(int studentId) {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the count of enrollments for a specific course.
     * @param courseId the course's ID
     * @return number of enrollments for the course
     */
    public int getEnrollmentCountByCourse(int courseId) {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the enrollment list is empty.
     * @return true if no enrollments exist, false otherwise
     */
    public boolean isEmpty() {
        return enrollments.isEmpty();
    }
}
