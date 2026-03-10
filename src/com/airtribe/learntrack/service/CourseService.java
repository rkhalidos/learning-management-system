package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;

/**
 * Service class for managing Course entities.
 * Handles all business logic related to courses.
 * Uses ArrayList to store course data in-memory.
 */
public class CourseService {

    // ArrayList to store courses (in-memory storage)
    private ArrayList<Course> courses;

    // Constructor
    public CourseService() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a new course to the system.
     * @param courseName name of the course
     * @param description course description
     * @param durationInWeeks duration in weeks
     * @return the created Course object
     * @throws InvalidInputException if input validation fails
     */
    public Course addCourse(String courseName, String description, int durationInWeeks) 
            throws InvalidInputException {
        // Validate inputs
        InputValidator.validateNotEmpty(courseName, "Course Name");
        InputValidator.validatePositiveInteger(durationInWeeks, "Duration in Weeks");

        // Generate unique ID and create course
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks);
        
        // Add to list
        courses.add(course);
        
        return course;
    }

    /**
     * Adds a new course without description (overloaded method).
     * @param courseName name of the course
     * @param durationInWeeks duration in weeks
     * @return the created Course object
     * @throws InvalidInputException if input validation fails
     */
    public Course addCourse(String courseName, int durationInWeeks) 
            throws InvalidInputException {
        return addCourse(courseName, "", durationInWeeks);
    }

    /**
     * Retrieves a course by ID.
     * @param courseId the course ID to search for
     * @return the Course if found
     * @throws EntityNotFoundException if course is not found
     */
    public Course getCourseById(int courseId) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        throw new EntityNotFoundException("Course", courseId);
    }

    /**
     * Returns all courses in the system.
     * @return ArrayList of all courses
     */
    public ArrayList<Course> getAllCourses() {
        return new ArrayList<>(courses); // Return a copy to prevent external modification
    }

    /**
     * Returns only active courses.
     * @return ArrayList of active courses
     */
    public ArrayList<Course> getActiveCourses() {
        ArrayList<Course> activeCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    /**
     * Updates an existing course's information.
     * @param courseId the course ID to update
     * @param courseName new course name (null to keep existing)
     * @param description new description (null to keep existing)
     * @param durationInWeeks new duration (0 or negative to keep existing)
     * @return the updated Course
     * @throws EntityNotFoundException if course is not found
     * @throws InvalidInputException if input validation fails
     */
    public Course updateCourse(int courseId, String courseName, String description, 
                                int durationInWeeks) 
            throws EntityNotFoundException, InvalidInputException {
        Course course = getCourseById(courseId);
        
        if (courseName != null && !courseName.trim().isEmpty()) {
            course.setCourseName(courseName);
        }
        if (description != null) {
            course.setDescription(description);
        }
        if (durationInWeeks > 0) {
            course.setDurationInWeeks(durationInWeeks);
        }
        
        return course;
    }

    /**
     * Deactivates a course (soft delete).
     * @param courseId the course ID to deactivate
     * @return the deactivated Course
     * @throws EntityNotFoundException if course is not found
     */
    public Course deactivateCourse(int courseId) throws EntityNotFoundException {
        Course course = getCourseById(courseId);
        course.setActive(false);
        return course;
    }

    /**
     * Activates a previously deactivated course.
     * @param courseId the course ID to activate
     * @return the activated Course
     * @throws EntityNotFoundException if course is not found
     */
    public Course activateCourse(int courseId) throws EntityNotFoundException {
        Course course = getCourseById(courseId);
        course.setActive(true);
        return course;
    }

    /**
     * Searches courses by name.
     * @param searchTerm the term to search for
     * @return ArrayList of matching courses
     */
    public ArrayList<Course> searchCoursesByName(String searchTerm) {
        ArrayList<Course> results = new ArrayList<>();
        String searchLower = searchTerm.toLowerCase();
        
        for (Course course : courses) {
            if (course.getCourseName().toLowerCase().contains(searchLower)) {
                results.add(course);
            }
        }
        return results;
    }

    /**
     * Returns courses filtered by duration range.
     * @param minWeeks minimum duration in weeks
     * @param maxWeeks maximum duration in weeks
     * @return ArrayList of courses within the duration range
     */
    public ArrayList<Course> getCoursesByDurationRange(int minWeeks, int maxWeeks) {
        ArrayList<Course> results = new ArrayList<>();
        
        for (Course course : courses) {
            int duration = course.getDurationInWeeks();
            if (duration >= minWeeks && duration <= maxWeeks) {
                results.add(course);
            }
        }
        return results;
    }

    /**
     * Returns the total count of courses.
     * @return total number of courses
     */
    public int getTotalCourseCount() {
        return courses.size();
    }

    /**
     * Returns the count of active courses.
     * @return number of active courses
     */
    public int getActiveCourseCount() {
        int count = 0;
        for (Course course : courses) {
            if (course.isActive()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if a course exists with the given ID.
     * @param courseId the course ID to check
     * @return true if course exists, false otherwise
     */
    public boolean courseExists(int courseId) {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the course list is empty.
     * @return true if no courses exist, false otherwise
     */
    public boolean isEmpty() {
        return courses.isEmpty();
    }
}
