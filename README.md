# LearnTrack - Student & Course Management System

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Complete-green)

A console-based Student & Course Management System built using Core Java. This project is designed for learning and practicing fundamental Java concepts including OOP principles, collections, and exception handling.

## 📋 Table of Contents

- [Features](#-features)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [How to Compile and Run](#-how-to-compile-and-run)
- [Usage Guide](#-usage-guide)
- [Learning Objectives](#-learning-objectives)
- [Documentation](#-documentation)
- [Technologies Used](#-technologies-used)

## ✨ Features

### Student Management
- Add new students with details (name, email, batch)
- View all students
- Search student by ID
- Update student information
- Activate/Deactivate students (soft delete)

### Course Management
- Add new courses with details (name, description, duration)
- View all courses
- Search course by ID
- Update course information
- Activate/Deactivate courses

### Enrollment Management
- Enroll students in courses
- View enrollments by student
- View enrollments by course
- View all enrollments
- Mark enrollment as completed
- Cancel enrollment

### System Features
- System statistics dashboard
- Input validation with meaningful error messages
- Graceful error handling
- Menu-driven console interface

## 📁 Project Structure

```
LearnTrack/
├── src/
│   └── com/
│       └── airtribe/
│           └── learntrack/
│               ├── entity/           # Data classes
│               │   ├── Person.java
│               │   ├── Student.java
│               │   ├── Trainer.java
│               │   ├── Course.java
│               │   ├── Enrollment.java
│               │   └── EnrollmentStatus.java
│               ├── exception/        # Custom exceptions
│               │   ├── EntityNotFoundException.java
│               │   └── InvalidInputException.java
│               ├── service/          # Business logic
│               │   ├── StudentService.java
│               │   ├── CourseService.java
│               │   └── EnrollmentService.java
│               ├── util/             # Utility classes
│               │   ├── IdGenerator.java
│               │   └── InputValidator.java
│               └── ui/               # Console interface
│                   └── Main.java
├── docs/                             # Documentation
│   ├── Setup_Instructions.md
│   ├── JVM_Basics.md
│   └── Design_Notes.md
├── out/                              # Compiled classes (generated)
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 17 or higher
- A terminal or command prompt
- (Optional) An IDE like VS Code, IntelliJ IDEA, or Eclipse

### Verify Java Installation

```bash
java -version
javac -version
```

Both commands should show version 17 or higher.

## 🔧 How to Compile and Run

### Using Command Line

1. **Navigate to the project directory:**
   ```bash
   cd LearnTrack
   ```

2. **Compile all Java files:**
   
   **Windows:**
   ```bash
   javac -d out src\com\airtribe\learntrack\entity\*.java src\com\airtribe\learntrack\exception\*.java src\com\airtribe\learntrack\util\*.java src\com\airtribe\learntrack\service\*.java src\com\airtribe\learntrack\ui\*.java
   ```

   **macOS/Linux:**
   ```bash
   javac -d out src/com/airtribe/learntrack/entity/*.java src/com/airtribe/learntrack/exception/*.java src/com/airtribe/learntrack/util/*.java src/com/airtribe/learntrack/service/*.java src/com/airtribe/learntrack/ui/*.java
   ```

3. **Run the application:**
   ```bash
   java -cp out com.airtribe.learntrack.ui.Main
   ```

### Using an IDE

1. Open the project in your IDE
2. Make sure the `src` folder is marked as the source root
3. Run `Main.java` located in `src/com/airtribe/learntrack/ui/`

## 📖 Usage Guide

When you run the application, you'll see the main menu:

```
========================================
             MAIN MENU
========================================
  1. Student Management
  2. Course Management
  3. Enrollment Management
  4. System Statistics
  0. Exit
----------------------------------------
Enter your choice: 
```

### Quick Start Example

1. **Add a Student**: Choose option 1 → then option 1 to add a new student
2. **Add a Course**: Choose option 2 → then option 1 to add a new course
3. **Enroll Student**: Choose option 3 → then option 1 to enroll the student in the course
4. **View Statistics**: Choose option 4 to see system statistics

## 📚 Learning Objectives

This project covers the following Core Java concepts:

### Java Fundamentals
- ✅ Packages and imports
- ✅ Classes and objects
- ✅ Main method structure
- ✅ Data types (primitives and references)
- ✅ Variables and their scopes

### OOP Principles
- ✅ **Encapsulation**: Private fields with public getters/setters
- ✅ **Inheritance**: Person → Student/Trainer hierarchy
- ✅ **Polymorphism**: Method overriding (`getDisplayName()`)
- ✅ **Abstraction**: Service layer hiding implementation details

### Constructors
- ✅ Default constructors
- ✅ Parameterized constructors
- ✅ Constructor overloading
- ✅ Using `super()` for inheritance

### Static Members
- ✅ Static fields (ID counters in `IdGenerator`)
- ✅ Static methods (utility methods)
- ✅ Static constants

### Collections
- ✅ ArrayList usage for dynamic data storage
- ✅ Iterating over collections
- ✅ Adding, removing, and searching elements

### Exception Handling
- ✅ Custom exceptions (`EntityNotFoundException`, `InvalidInputException`)
- ✅ Try-catch blocks
- ✅ Throwing and catching exceptions
- ✅ Meaningful error messages

### Control Flow
- ✅ If-else statements
- ✅ Switch statements
- ✅ For and while loops
- ✅ Menu-driven application flow

## 📄 Documentation

Detailed documentation is available in the `docs/` folder:

| Document | Description |
|----------|-------------|
| [Setup_Instructions.md](docs/Setup_Instructions.md) | Detailed setup and installation guide |
| [JVM_Basics.md](docs/JVM_Basics.md) | Understanding JDK, JRE, JVM, and bytecode |
| [Design_Notes.md](docs/Design_Notes.md) | Design decisions and explanations |

## 🛠 Technologies Used

- **Language**: Java 17
- **Build**: Command-line compilation (no build tools required)
- **Data Storage**: In-memory (ArrayList)
- **Interface**: Console-based (Scanner for input)

## 📊 Project Highlights

| Feature | Implementation |
|---------|---------------|
| Entities | 6 classes (Person, Student, Trainer, Course, Enrollment, EnrollmentStatus) |
| Services | 3 service classes for business logic |
| Exceptions | 2 custom exception classes |
| Utilities | 2 utility classes (IdGenerator, InputValidator) |
| Lines of Code | ~1500+ lines |

## 🎓 Designed For

This project is designed for:
- Java beginners learning core concepts
- Practice with OOP principles
- Understanding project structure and organization
- Building menu-driven console applications

## 📝 License

This project is licensed under the MIT License - feel free to use it for learning purposes.

---

**Happy Coding! 🚀**
