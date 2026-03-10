package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;

/**
 * Service class for managing Student entities.
 * Handles all business logic related to students.
 * Uses ArrayList to store student data in-memory.
 */
public class StudentService {

    // ArrayList to store students (in-memory storage)
    private ArrayList<Student> students;

    // Constructor
    public StudentService() {
        this.students = new ArrayList<>();
    }

    /**
     * Adds a new student to the system.
     * @param firstName student's first name
     * @param lastName student's last name
     * @param email student's email
     * @param batch student's batch
     * @return the created Student object
     * @throws InvalidInputException if input validation fails
     */
    public Student addStudent(String firstName, String lastName, String email, String batch) 
            throws InvalidInputException {
        // Validate inputs
        InputValidator.validateNotEmpty(firstName, "First Name");
        InputValidator.validateNotEmpty(lastName, "Last Name");
        InputValidator.validateEmail(email);
        InputValidator.validateNotEmpty(batch, "Batch");

        // Generate unique ID and create student
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        
        // Add to list
        students.add(student);
        
        return student;
    }

    /**
     * Adds a new student without email (overloaded method).
     * @param firstName student's first name
     * @param lastName student's last name
     * @param batch student's batch
     * @return the created Student object
     * @throws InvalidInputException if input validation fails
     */
    public Student addStudent(String firstName, String lastName, String batch) 
            throws InvalidInputException {
        return addStudent(firstName, lastName, "", batch);
    }

    /**
     * Retrieves a student by ID.
     * @param studentId the student ID to search for
     * @return the Student if found
     * @throws EntityNotFoundException if student is not found
     */
    public Student getStudentById(int studentId) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student", studentId);
    }

    /**
     * Returns all students in the system.
     * @return ArrayList of all students
     */
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent external modification
    }

    /**
     * Returns only active students.
     * @return ArrayList of active students
     */
    public ArrayList<Student> getActiveStudents() {
        ArrayList<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    /**
     * Updates an existing student's information.
     * @param studentId the student ID to update
     * @param firstName new first name (null to keep existing)
     * @param lastName new last name (null to keep existing)
     * @param email new email (null to keep existing)
     * @param batch new batch (null to keep existing)
     * @return the updated Student
     * @throws EntityNotFoundException if student is not found
     * @throws InvalidInputException if input validation fails
     */
    public Student updateStudent(int studentId, String firstName, String lastName, 
                                  String email, String batch) 
            throws EntityNotFoundException, InvalidInputException {
        Student student = getStudentById(studentId);
        
        if (firstName != null && !firstName.trim().isEmpty()) {
            student.setFirstName(firstName);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            student.setLastName(lastName);
        }
        if (email != null) {
            if (!email.trim().isEmpty()) {
                InputValidator.validateEmail(email);
            }
            student.setEmail(email);
        }
        if (batch != null && !batch.trim().isEmpty()) {
            student.setBatch(batch);
        }
        
        return student;
    }

    /**
     * Deactivates a student (soft delete).
     * @param studentId the student ID to deactivate
     * @return the deactivated Student
     * @throws EntityNotFoundException if student is not found
     */
    public Student deactivateStudent(int studentId) throws EntityNotFoundException {
        Student student = getStudentById(studentId);
        student.setActive(false);
        return student;
    }

    /**
     * Activates a previously deactivated student.
     * @param studentId the student ID to activate
     * @return the activated Student
     * @throws EntityNotFoundException if student is not found
     */
    public Student activateStudent(int studentId) throws EntityNotFoundException {
        Student student = getStudentById(studentId);
        student.setActive(true);
        return student;
    }

    /**
     * Searches students by name (first or last name contains the search term).
     * @param searchTerm the term to search for
     * @return ArrayList of matching students
     */
    public ArrayList<Student> searchStudentsByName(String searchTerm) {
        ArrayList<Student> results = new ArrayList<>();
        String searchLower = searchTerm.toLowerCase();
        
        for (Student student : students) {
            if (student.getFirstName().toLowerCase().contains(searchLower) ||
                student.getLastName().toLowerCase().contains(searchLower)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Searches students by batch.
     * @param batch the batch to search for
     * @return ArrayList of students in the specified batch
     */
    public ArrayList<Student> getStudentsByBatch(String batch) {
        ArrayList<Student> results = new ArrayList<>();
        
        for (Student student : students) {
            if (student.getBatch().equalsIgnoreCase(batch)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Returns the total count of students.
     * @return total number of students
     */
    public int getTotalStudentCount() {
        return students.size();
    }

    /**
     * Returns the count of active students.
     * @return number of active students
     */
    public int getActiveStudentCount() {
        int count = 0;
        for (Student student : students) {
            if (student.isActive()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if a student exists with the given ID.
     * @param studentId the student ID to check
     * @return true if student exists, false otherwise
     */
    public boolean studentExists(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the student list is empty.
     * @return true if no students exist, false otherwise
     */
    public boolean isEmpty() {
        return students.isEmpty();
    }
}
