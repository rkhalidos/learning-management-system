# Setup Instructions for LearnTrack

## JDK Version Used
- **JDK Version**: Java SE 17 (LTS) or higher
- **Recommended**: OpenJDK 17 or Oracle JDK 17

## Prerequisites
1. Java Development Kit (JDK) 17 or higher installed
2. A text editor or IDE (VS Code, IntelliJ IDEA, Eclipse, etc.)
3. Command line access (Terminal, Command Prompt, or PowerShell)

## Installation Steps

### Step 1: Verify Java Installation
Open a terminal/command prompt and run:
```bash
java -version
javac -version
```

You should see output similar to:
```
java version "17.0.x" 2024-xx-xx LTS
Java(TM) SE Runtime Environment (build 17.0.x+xx-LTS-xxx)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.x+xx-LTS-xxx, mixed mode, sharing)
```

### Step 2: Set JAVA_HOME Environment Variable
**Windows:**
1. Open System Properties > Environment Variables
2. Add new System Variable:
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-17` (adjust to your JDK path)
3. Add `%JAVA_HOME%\bin` to your PATH variable

**macOS/Linux:**
Add to your `~/.bashrc` or `~/.zshrc`:
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

### Step 3: Clone/Download the Project
Navigate to your desired directory and clone or download the LearnTrack project.

### Step 4: Compile the Project

**Option A: Using Command Line**

Navigate to the project root directory and compile all Java files:

```bash
# Windows
javac -d out src\com\airtribe\learntrack\entity\*.java src\com\airtribe\learntrack\exception\*.java src\com\airtribe\learntrack\util\*.java src\com\airtribe\learntrack\service\*.java src\com\airtribe\learntrack\ui\*.java

# macOS/Linux
javac -d out src/com/airtribe/learntrack/entity/*.java src/com/airtribe/learntrack/exception/*.java src/com/airtribe/learntrack/util/*.java src/com/airtribe/learntrack/service/*.java src/com/airtribe/learntrack/ui/*.java
```

**Option B: Using IDE**
1. Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code with Java extensions)
2. The IDE will automatically compile the project
3. Right-click on `Main.java` and select "Run"

### Step 5: Run the Application

```bash
# Windows
java -cp out com.airtribe.learntrack.ui.Main

# macOS/Linux
java -cp out com.airtribe.learntrack.ui.Main
```

## Hello World Program Verification

To verify your Java setup is working correctly, create a simple test:

1. Create a file named `HelloWorld.java`:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println("Java is working correctly!");
        System.out.println("Java Version: " + System.getProperty("java.version"));
    }
}
```

2. Compile it:
```bash
javac HelloWorld.java
```

3. Run it:
```bash
java HelloWorld
```

4. Expected output:
```
Hello, World!
Java is working correctly!
Java Version: 17.0.x
```

## Troubleshooting

### Common Issues

1. **'javac' is not recognized as an internal or external command**
   - Ensure JDK is installed (not just JRE)
   - Verify JAVA_HOME is set correctly
   - Ensure `%JAVA_HOME%\bin` is in your PATH

2. **Class not found exception**
   - Make sure you're running from the correct directory
   - Verify the classpath (-cp) is set correctly

3. **Version mismatch errors**
   - Ensure you're using JDK 17 or higher
   - Recompile all files with the same JDK version

## Project Structure
```
LearnTrack/
├── src/
│   └── com/
│       └── airtribe/
│           └── learntrack/
│               ├── entity/        # Data classes (Student, Course, Enrollment, etc.)
│               ├── exception/     # Custom exceptions
│               ├── service/       # Business logic (StudentService, etc.)
│               ├── util/          # Utility classes (IdGenerator, InputValidator)
│               └── ui/            # Console UI (Main.java)
├── docs/                          # Documentation
│   ├── Setup_Instructions.md
│   ├── JVM_Basics.md
│   └── Design_Notes.md
├── out/                           # Compiled .class files (created after compilation)
└── README.md
