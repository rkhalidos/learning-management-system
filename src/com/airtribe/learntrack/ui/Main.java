package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the LearnTrack console application.
 * Provides a menu-driven interface for managing students, courses, and enrollments.
 */
public class Main {

    // Services for handling business logic
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;

    // Scanner for user input
    private static Scanner scanner;

    // Constants for menu formatting
    private static final String LINE_SEPARATOR = "========================================";
    private static final String SUB_LINE_SEPARATOR = "----------------------------------------";

    public static void main(String[] args) {
        // Initialize services
        studentService = new StudentService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService(studentService, courseService);
        scanner = new Scanner(System.in);

        // Display welcome message
        displayWelcomeMessage();

        // Main application loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    handleStudentManagement();
                    break;
                case 2:
                    handleCourseManagement();
                    break;
                case 3:
                    handleEnrollmentManagement();
                    break;
                case 4:
                    displaySystemStatistics();
                    break;
                case 0:
                    running = false;
                    displayExitMessage();
                    break;
                default:
                    System.out.println("\n[Error] Invalid option. Please enter a number between 0 and 4.");
            }
        }

        scanner.close();
    }

    // ==================== DISPLAY METHODS ====================

    private static void displayWelcomeMessage() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("    WELCOME TO LEARNTRACK");
        System.out.println("    Student & Course Management System");
        System.out.println(LINE_SEPARATOR);
        System.out.println("Version: 1.0.0");
        System.out.println("Developed for Core Java Practice\n");
    }

    private static void displayExitMessage() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("Thank you for using LearnTrack!");
        System.out.println("Goodbye!");
        System.out.println(LINE_SEPARATOR + "\n");
    }

    private static void displayMainMenu() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("             MAIN MENU");
        System.out.println(LINE_SEPARATOR);
        System.out.println("  1. Student Management");
        System.out.println("  2. Course Management");
        System.out.println("  3. Enrollment Management");
        System.out.println("  4. System Statistics");
        System.out.println("  0. Exit");
        System.out.println(SUB_LINE_SEPARATOR);
    }

    // ==================== STUDENT MANAGEMENT ====================

    private static void handleStudentManagement() {
        boolean inStudentMenu = true;
        while (inStudentMenu) {
            displayStudentMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deactivateStudent();
                    break;
                case 6:
                    activateStudent();
                    break;
                case 0:
                    inStudentMenu = false;
                    break;
                default:
                    System.out.println("\n[Error] Invalid option. Please try again.");
            }
        }
    }

    private static void displayStudentMenu() {
        System.out.println("\n" + SUB_LINE_SEPARATOR);
        System.out.println("       STUDENT MANAGEMENT");
        System.out.println(SUB_LINE_SEPARATOR);
        System.out.println("  1. Add New Student");
        System.out.println("  2. View All Students");
        System.out.println("  3. Search Student by ID");
        System.out.println("  4. Update Student");
        System.out.println("  5. Deactivate Student");
        System.out.println("  6. Activate Student");
        System.out.println("  0. Back to Main Menu");
        System.out.println(SUB_LINE_SEPARATOR);
    }

    private static void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        try {
            String firstName = readStringInput("First Name: ");
            String lastName = readStringInput("Last Name: ");
            String email = readStringInput("Email (press Enter to skip): ");
            String batch = readStringInput("Batch: ");

            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("\n[Success] Student added successfully!");
            System.out.println("Student ID: " + student.getId());
            System.out.println(student);
        } catch (InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        ArrayList<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }

        System.out.println("Total Students: " + students.size());
        System.out.println(SUB_LINE_SEPARATOR);
        for (Student student : students) {
            displayStudentInfo(student);
        }
    }

    private static void searchStudentById() {
        System.out.println("\n--- Search Student by ID ---");
        int studentId = readIntInput("Enter Student ID: ");

        try {
            Student student = studentService.getStudentById(studentId);
            System.out.println("\n[Found] Student Details:");
            displayStudentInfo(student);
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int studentId = readIntInput("Enter Student ID to update: ");

        try {
            Student student = studentService.getStudentById(studentId);
            System.out.println("Current Details:");
            displayStudentInfo(student);

            System.out.println("\nEnter new values (press Enter to keep current value):");
            String firstName = readStringInput("First Name [" + student.getFirstName() + "]: ");
            String lastName = readStringInput("Last Name [" + student.getLastName() + "]: ");
            String email = readStringInput("Email [" + student.getEmail() + "]: ");
            String batch = readStringInput("Batch [" + student.getBatch() + "]: ");

            studentService.updateStudent(studentId, 
                firstName.isEmpty() ? null : firstName,
                lastName.isEmpty() ? null : lastName,
                email.isEmpty() ? null : email,
                batch.isEmpty() ? null : batch);

            System.out.println("\n[Success] Student updated successfully!");
            System.out.println("Updated Details:");
            displayStudentInfo(studentService.getStudentById(studentId));
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void deactivateStudent() {
        System.out.println("\n--- Deactivate Student ---");
        int studentId = readIntInput("Enter Student ID to deactivate: ");

        try {
            Student student = studentService.deactivateStudent(studentId);
            System.out.println("\n[Success] Student deactivated successfully!");
            displayStudentInfo(student);
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void activateStudent() {
        System.out.println("\n--- Activate Student ---");
        int studentId = readIntInput("Enter Student ID to activate: ");

        try {
            Student student = studentService.activateStudent(studentId);
            System.out.println("\n[Success] Student activated successfully!");
            displayStudentInfo(student);
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void displayStudentInfo(Student student) {
        String status = student.isActive() ? "ACTIVE" : "INACTIVE";
        System.out.println("  ID: " + student.getId() + 
                          " | Name: " + student.getFirstName() + " " + student.getLastName() +
                          " | Email: " + (student.getEmail().isEmpty() ? "N/A" : student.getEmail()) +
                          " | Batch: " + student.getBatch() +
                          " | Status: " + status);
    }

    // ==================== COURSE MANAGEMENT ====================

    private static void handleCourseManagement() {
        boolean inCourseMenu = true;
        while (inCourseMenu) {
            displayCourseMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    searchCourseById();
                    break;
                case 4:
                    updateCourse();
                    break;
                case 5:
                    deactivateCourse();
                    break;
                case 6:
                    activateCourse();
                    break;
                case 0:
                    inCourseMenu = false;
                    break;
                default:
                    System.out.println("\n[Error] Invalid option. Please try again.");
            }
        }
    }

    private static void displayCourseMenu() {
        System.out.println("\n" + SUB_LINE_SEPARATOR);
        System.out.println("        COURSE MANAGEMENT");
        System.out.println(SUB_LINE_SEPARATOR);
        System.out.println("  1. Add New Course");
        System.out.println("  2. View All Courses");
        System.out.println("  3. Search Course by ID");
        System.out.println("  4. Update Course");
        System.out.println("  5. Deactivate Course");
        System.out.println("  6. Activate Course");
        System.out.println("  0. Back to Main Menu");
        System.out.println(SUB_LINE_SEPARATOR);
    }

    private static void addNewCourse() {
        System.out.println("\n--- Add New Course ---");
        try {
            String courseName = readStringInput("Course Name: ");
            String description = readStringInput("Description (press Enter to skip): ");
            int duration = readIntInput("Duration (in weeks): ");

            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println("\n[Success] Course added successfully!");
            System.out.println("Course ID: " + course.getId());
            System.out.println(course);
        } catch (InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        ArrayList<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found in the system.");
            return;
        }

        System.out.println("Total Courses: " + courses.size());
        System.out.println(SUB_LINE_SEPARATOR);
        for (Course course : courses) {
            displayCourseInfo(course);
        }
    }

    private static void searchCourseById() {
        System.out.println("\n--- Search Course by ID ---");
        int courseId = readIntInput("Enter Course ID: ");

        try {
            Course course = courseService.getCourseById(courseId);
            System.out.println("\n[Found] Course Details:");
            displayCourseInfo(course);
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void updateCourse() {
        System.out.println("\n--- Update Course ---");
        int courseId = readIntInput("Enter Course ID to update: ");

        try {
            Course course = courseService.getCourseById(courseId);
            System.out.println("Current Details:");
            displayCourseInfo(course);

            System.out.println("\nEnter new values (press Enter to keep current value):");
            String courseName = readStringInput("Course Name [" + course.getCourseName() + "]: ");
            String description = readStringInput("Description [" + course.getDescription() + "]: ");
            String durationStr = readStringInput("Duration in weeks [" + course.getDurationInWeeks() + "]: ");
            int duration = durationStr.isEmpty() ? 0 : Integer.parseInt(durationStr);

            courseService.updateCourse(courseId,
                courseName.isEmpty() ? null : courseName,
                description.isEmpty() ? null : description,
                duration);

            System.out.println("\n[Success] Course updated successfully!");
            System.out.println("Updated Details:");
            displayCourseInfo(courseService.getCourseById(courseId));
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n[Error] Invalid duration. Please enter a valid number.");
        }
    }

    private static void deactivateCourse() {
        System.out.println("\n--- Deactivate Course ---");
        int courseId = readIntInput("Enter Course ID to deactivate: ");

        try {
            Course course = courseService.deactivateCourse(courseId);
            System.out.println("\n[Success] Course deactivated successfully!");
            displayCourseInfo(course);
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void activateCourse() {
        System.out.println("\n--- Activate Course ---");
        int courseId = readIntInput("Enter Course ID to activate: ");

        try {
            Course course = courseService.activateCourse(courseId);
            System.out.println("\n[Success] Course activated successfully!");
            displayCourseInfo(course);
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void displayCourseInfo(Course course) {
        String status = course.isActive() ? "ACTIVE" : "INACTIVE";
        System.out.println("  ID: " + course.getId() + 
                          " | Name: " + course.getCourseName() +
                          " | Duration: " + course.getDurationInWeeks() + " weeks" +
                          " | Status: " + status);
        if (!course.getDescription().isEmpty()) {
            System.out.println("    Description: " + course.getDescription());
        }
    }

    // ==================== ENROLLMENT MANAGEMENT ====================

    private static void handleEnrollmentManagement() {
        boolean inEnrollmentMenu = true;
        while (inEnrollmentMenu) {
            displayEnrollmentMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    enrollStudentInCourse();
                    break;
                case 2:
                    viewEnrollmentsByStudent();
                    break;
                case 3:
                    viewEnrollmentsByCourse();
                    break;
                case 4:
                    viewAllEnrollments();
                    break;
                case 5:
                    markEnrollmentAsCompleted();
                    break;
                case 6:
                    cancelEnrollment();
                    break;
                case 0:
                    inEnrollmentMenu = false;
                    break;
                default:
                    System.out.println("\n[Error] Invalid option. Please try again.");
            }
        }
    }

    private static void displayEnrollmentMenu() {
        System.out.println("\n" + SUB_LINE_SEPARATOR);
        System.out.println("     ENROLLMENT MANAGEMENT");
        System.out.println(SUB_LINE_SEPARATOR);
        System.out.println("  1. Enroll Student in Course");
        System.out.println("  2. View Enrollments by Student");
        System.out.println("  3. View Enrollments by Course");
        System.out.println("  4. View All Enrollments");
        System.out.println("  5. Mark Enrollment as Completed");
        System.out.println("  6. Cancel Enrollment");
        System.out.println("  0. Back to Main Menu");
        System.out.println(SUB_LINE_SEPARATOR);
    }

    private static void enrollStudentInCourse() {
        System.out.println("\n--- Enroll Student in Course ---");

        // Show available students
        ArrayList<Student> students = studentService.getActiveStudents();
        if (students.isEmpty()) {
            System.out.println("No active students available. Please add students first.");
            return;
        }
        System.out.println("Available Students:");
        for (Student student : students) {
            System.out.println("  ID: " + student.getId() + " - " + student.getFirstName() + " " + student.getLastName());
        }

        // Show available courses
        ArrayList<Course> courses = courseService.getActiveCourses();
        if (courses.isEmpty()) {
            System.out.println("No active courses available. Please add courses first.");
            return;
        }
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("  ID: " + course.getId() + " - " + course.getCourseName());
        }

        try {
            int studentId = readIntInput("\nEnter Student ID: ");
            int courseId = readIntInput("Enter Course ID: ");

            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("\n[Success] Student enrolled successfully!");
            displayEnrollmentInfo(enrollment);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void viewEnrollmentsByStudent() {
        System.out.println("\n--- View Enrollments by Student ---");
        int studentId = readIntInput("Enter Student ID: ");

        try {
            Student student = studentService.getStudentById(studentId);
            System.out.println("\nEnrollments for: " + student.getFirstName() + " " + student.getLastName());

            ArrayList<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
                return;
            }

            for (Enrollment enrollment : enrollments) {
                displayEnrollmentInfoWithCourse(enrollment);
            }
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void viewEnrollmentsByCourse() {
        System.out.println("\n--- View Enrollments by Course ---");
        int courseId = readIntInput("Enter Course ID: ");

        try {
            Course course = courseService.getCourseById(courseId);
            System.out.println("\nEnrollments for: " + course.getCourseName());

            ArrayList<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this course.");
                return;
            }

            for (Enrollment enrollment : enrollments) {
                displayEnrollmentInfoWithStudent(enrollment);
            }
        } catch (EntityNotFoundException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void viewAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        ArrayList<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found in the system.");
            return;
        }

        System.out.println("Total Enrollments: " + enrollments.size());
        System.out.println(SUB_LINE_SEPARATOR);
        for (Enrollment enrollment : enrollments) {
            displayFullEnrollmentInfo(enrollment);
        }
    }

    private static void markEnrollmentAsCompleted() {
        System.out.println("\n--- Mark Enrollment as Completed ---");
        int enrollmentId = readIntInput("Enter Enrollment ID: ");

        try {
            Enrollment enrollment = enrollmentService.markAsCompleted(enrollmentId);
            System.out.println("\n[Success] Enrollment marked as completed!");
            displayEnrollmentInfo(enrollment);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void cancelEnrollment() {
        System.out.println("\n--- Cancel Enrollment ---");
        int enrollmentId = readIntInput("Enter Enrollment ID: ");

        try {
            Enrollment enrollment = enrollmentService.cancelEnrollment(enrollmentId);
            System.out.println("\n[Success] Enrollment cancelled!");
            displayEnrollmentInfo(enrollment);
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("\n[Error] " + e.getMessage());
        }
    }

    private static void displayEnrollmentInfo(Enrollment enrollment) {
        System.out.println("  Enrollment ID: " + enrollment.getId() +
                          " | Student ID: " + enrollment.getStudentId() +
                          " | Course ID: " + enrollment.getCourseId() +
                          " | Date: " + enrollment.getFormattedEnrollmentDate() +
                          " | Status: " + enrollment.getStatus());
    }

    private static void displayEnrollmentInfoWithCourse(Enrollment enrollment) {
        try {
            Course course = courseService.getCourseById(enrollment.getCourseId());
            System.out.println("  Enrollment ID: " + enrollment.getId() +
                              " | Course: " + course.getCourseName() +
                              " | Date: " + enrollment.getFormattedEnrollmentDate() +
                              " | Status: " + enrollment.getStatus());
        } catch (EntityNotFoundException e) {
            displayEnrollmentInfo(enrollment);
        }
    }

    private static void displayEnrollmentInfoWithStudent(Enrollment enrollment) {
        try {
            Student student = studentService.getStudentById(enrollment.getStudentId());
            System.out.println("  Enrollment ID: " + enrollment.getId() +
                              " | Student: " + student.getFirstName() + " " + student.getLastName() +
                              " | Date: " + enrollment.getFormattedEnrollmentDate() +
                              " | Status: " + enrollment.getStatus());
        } catch (EntityNotFoundException e) {
            displayEnrollmentInfo(enrollment);
        }
    }

    private static void displayFullEnrollmentInfo(Enrollment enrollment) {
        try {
            Student student = studentService.getStudentById(enrollment.getStudentId());
            Course course = courseService.getCourseById(enrollment.getCourseId());
            System.out.println("  ID: " + enrollment.getId() +
                              " | Student: " + student.getFirstName() + " " + student.getLastName() +
                              " | Course: " + course.getCourseName() +
                              " | Date: " + enrollment.getFormattedEnrollmentDate() +
                              " | Status: " + enrollment.getStatus());
        } catch (EntityNotFoundException e) {
            displayEnrollmentInfo(enrollment);
        }
    }

    // ==================== STATISTICS ====================

    private static void displaySystemStatistics() {
        System.out.println("\n" + LINE_SEPARATOR);
        System.out.println("          SYSTEM STATISTICS");
        System.out.println(LINE_SEPARATOR);

        // Student statistics
        System.out.println("\nSTUDENTS:");
        System.out.println("  Total Students: " + studentService.getTotalStudentCount());
        System.out.println("  Active Students: " + studentService.getActiveStudentCount());
        System.out.println("  Inactive Students: " + 
                          (studentService.getTotalStudentCount() - studentService.getActiveStudentCount()));

        // Course statistics
        System.out.println("\nCOURSES:");
        System.out.println("  Total Courses: " + courseService.getTotalCourseCount());
        System.out.println("  Active Courses: " + courseService.getActiveCourseCount());
        System.out.println("  Inactive Courses: " + 
                          (courseService.getTotalCourseCount() - courseService.getActiveCourseCount()));

        // Enrollment statistics
        System.out.println("\nENROLLMENTS:");
        System.out.println("  Total Enrollments: " + enrollmentService.getTotalEnrollmentCount());
        System.out.println("  Active Enrollments: " + enrollmentService.getActiveEnrollmentCount());
        System.out.println("  Completed Enrollments: " + 
                          enrollmentService.getEnrollmentsByStatus(EnrollmentStatus.COMPLETED).size());
        System.out.println("  Cancelled Enrollments: " + 
                          enrollmentService.getEnrollmentsByStatus(EnrollmentStatus.CANCELLED).size());

        System.out.println("\n" + LINE_SEPARATOR);
    }

    // ==================== INPUT HELPER METHODS ====================

    /**
     * Reads an integer input from the user with error handling.
     * @param prompt the prompt to display
     * @return the integer entered by the user
     */
    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[Error] Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a string input from the user.
     * @param prompt the prompt to display
     * @return the string entered by the user
     */
    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
