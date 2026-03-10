# Design Notes - LearnTrack Project

This document explains the key design decisions made in the LearnTrack project, focusing on why certain approaches were chosen over alternatives.

---

## Why ArrayList Instead of Array?

### The Problem with Arrays

Traditional arrays in Java have several limitations:

1. **Fixed Size**: Once you create an array, its size cannot be changed
   ```java
   // Array with fixed size of 10
   Student[] students = new Student[10];
   // What if we need to add an 11th student? We must create a new array!
   ```

2. **Manual Management**: Adding/removing elements requires manual index management
   ```java
   // To "delete" element at index 3, we must shift all elements
   for (int i = 3; i < array.length - 1; i++) {
       array[i] = array[i + 1];
   }
   ```

3. **No Built-in Methods**: Arrays don't have convenient methods for common operations

### Why ArrayList is Better for This Project

`ArrayList` from `java.util` solves these problems:

1. **Dynamic Sizing**: Automatically grows and shrinks as needed
   ```java
   ArrayList<Student> students = new ArrayList<>();
   students.add(student1);  // Size is now 1
   students.add(student2);  // Size is now 2
   // No need to specify initial capacity
   ```

2. **Built-in Methods**: Provides convenient methods for common operations
   ```java
   students.add(student);      // Add element
   students.remove(student);   // Remove element
   students.get(0);            // Access by index
   students.size();            // Get current size
   students.isEmpty();         // Check if empty
   ```

3. **Type Safety with Generics**: Ensures only correct types are stored
   ```java
   ArrayList<Student> students = new ArrayList<>();
   students.add(new Student());  // ✓ Allowed
   students.add(new Course());   // ✗ Compile error!
   ```

### Where We Use ArrayList in LearnTrack

- `StudentService`: Stores all students in `ArrayList<Student>`
- `CourseService`: Stores all courses in `ArrayList<Course>`
- `EnrollmentService`: Stores all enrollments in `ArrayList<Enrollment>`

This allows us to dynamically add, remove, and query entities without worrying about array bounds or resizing.

---

## Where We Used Static Members and Why

### Understanding Static vs Instance Members

- **Instance members**: Each object gets its own copy
- **Static members**: Shared across all instances of a class, belong to the class itself

### Static Members in IdGenerator

The `IdGenerator` utility class is the primary example of static member usage:

```java
public class IdGenerator {
    // Static counters - shared across the entire application
    private static int studentIdCounter = 0;
    private static int courseIdCounter = 0;
    private static int enrollmentIdCounter = 0;

    // Static methods - can be called without creating an instance
    public static int getNextStudentId() {
        return ++studentIdCounter;
    }
}
```

**Why Static Here?**

1. **Single Source of Truth**: We need exactly one counter for each entity type across the entire application. If these were instance variables, each `IdGenerator` object would have its own counter, leading to duplicate IDs.

2. **No Object Creation Needed**: We can call `IdGenerator.getNextStudentId()` directly without creating an `IdGenerator` object. This makes sense because the class is a utility, not something we need multiple instances of.

3. **Global State Management**: ID counters need to persist throughout the application's lifetime and be accessible from anywhere.

### Static Constants in Main Class

```java
public class Main {
    private static final String LINE_SEPARATOR = "========================================";
    private static final String SUB_LINE_SEPARATOR = "----------------------------------------";
}
```

**Why Static Here?**

- These are constants that don't change
- They're used throughout the class for consistent formatting
- Using `static final` makes them compile-time constants, which is memory-efficient

### Private Constructor Pattern

```java
public class IdGenerator {
    private IdGenerator() {
        // Prevent instantiation
    }
}
```

This pattern prevents anyone from creating instances of utility classes that should only have static members.

---

## Where We Used Inheritance and What We Gained

### The Person → Student/Trainer Hierarchy

```
        Person (Base Class)
       /                   \
   Student                Trainer
(extends Person)      (extends Person)
```

### What's in the Base Class (Person)

```java
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
}
```

### What Student Adds

```java
public class Student extends Person {
    private String batch;
    private boolean active;
    
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [Batch: " + batch + "]";
    }
}
```

### Benefits We Gained from Inheritance

1. **Code Reuse**: 
   - Common fields (`id`, `firstName`, `lastName`, `email`) are defined once in `Person`
   - Both `Student` and `Trainer` automatically have these fields
   - No need to duplicate getter/setter code

2. **Consistency**:
   - All "people" in the system have the same basic structure
   - Changes to common behavior can be made in one place

3. **Polymorphism**:
   - We can treat Students and Trainers as People when needed
   - Each subclass can have its own implementation of `getDisplayName()`
   ```java
   Person person = new Student(1, "John", "Doe", "john@email.com", "Batch2024");
   System.out.println(person.getDisplayName()); 
   // Output: "John Doe [Batch: Batch2024]" - Student's version is called
   ```

4. **Extensibility**:
   - Easy to add new types of people (e.g., Admin, Manager) in the future
   - New types automatically inherit common functionality

### The `super` Keyword Usage

```java
public Student(int id, String firstName, String lastName, String email, String batch) {
    super(id, firstName, lastName, email);  // Calls Person's constructor
    this.batch = batch;
    this.active = true;
}
```

Using `super()` allows us to initialize the parent class's fields through its constructor, ensuring proper initialization of the inheritance chain.

---

## Clean Code Principles Applied

### 1. Meaningful Names
```java
// Bad
public Student f(int i) { ... }

// Good
public Student getStudentById(int studentId) { ... }
```

### 2. Small, Focused Methods
Each method does one thing well:
- `addStudent()` - only handles adding
- `getStudentById()` - only handles retrieval
- `deactivateStudent()` - only handles deactivation

### 3. Separation of Concerns

| Layer | Responsibility |
|-------|---------------|
| `entity` | Data structures (what data looks like) |
| `service` | Business logic (what operations are allowed) |
| `ui` | User interaction (how to interact with users) |
| `exception` | Error handling (what can go wrong) |
| `util` | Utilities (helper functions) |

### 4. Encapsulation
All entity fields are `private` with public getters/setters:
```java
private String firstName;  // Private field

public String getFirstName() {     // Public getter
    return firstName;
}

public void setFirstName(String firstName) {  // Public setter
    this.firstName = firstName;
}
```

This allows us to:
- Add validation in setters later without changing other code
- Change internal representation without affecting external code
- Control access to data

---

## Exception Handling Strategy

### Custom Exceptions

We created two custom exceptions:

1. **EntityNotFoundException**: When a requested entity doesn't exist
   ```java
   throw new EntityNotFoundException("Student", studentId);
   // Message: "Student with ID 5 not found."
   ```

2. **InvalidInputException**: When user provides invalid data
   ```java
   throw new InvalidInputException("email", "invalid-email", "Invalid email format.");
   ```

### Why Custom Exceptions?

- **Clarity**: The exception name tells you what went wrong
- **Additional Context**: We can store relevant information (entity type, ID, field name)
- **Consistent Handling**: UI can catch specific exceptions and show appropriate messages

### Try-Catch in UI Layer

```java
try {
    Student student = studentService.getStudentById(studentId);
    // Show student
} catch (EntityNotFoundException e) {
    System.out.println("[Error] " + e.getMessage());
}
```

The UI layer catches exceptions and shows user-friendly messages instead of letting the program crash.
