# CampusPulse Project Report

**Course:** Programming in Java  
**Project:** Programming in Java - Evaluated Project  
**Student Name:** ______________________________  
**Student ID:** ______________________________  
**Submission Date:** ______________________________

## 1. Cover Page

### CampusPulse
### A Java-Based Campus Event and Participation Management System

CampusPulse is a console-based Java application that helps campus coordinators manage events, student registrations, capacity limits, and participation reports. It was developed for the Programming in Java evaluated project.

**Repository:** https://github.com/rakshika2639/vityarthiproject

## 2. Introduction

Campus activities are commonly managed using informal lists or separate spreadsheets. This makes it difficult to maintain accurate event details, avoid duplicate registrations, prevent overbooking, and prepare participation summaries. CampusPulse provides a small but complete software solution for this workflow.

The project applies Java classes, objects, interfaces, collections, date/time APIs, file handling, validation, exception handling, and modular design. It is deliberately dependency-free so that it can be compiled with the standard Java Development Kit.

## 3. Problem Statement

Campus event coordinators need a reliable way to publish events, manage limited seats, register students, and understand participation. Manual processes can produce duplicate entries, registrations beyond capacity, outdated event details, and incomplete reports.

CampusPulse addresses these problems with a validated application that stores event and registration data in CSV files and provides a consistent menu-driven workflow.

## 4. Objectives

1. Build an original Java application for a meaningful campus problem.
2. Apply object-oriented programming and separation of responsibilities.
3. Provide at least three functional modules with clear input and output.
4. Validate input and prevent invalid business states.
5. Persist data between application runs.
6. Generate useful event participation reports.
7. Demonstrate testing and version control through GitHub.

## 5. Functional Requirements

| ID | Requirement | Implementation |
| --- | --- | --- |
| FR-01 | Create, list, update, and cancel events. | `EventService` and `ConsoleMenu` |
| FR-02 | Validate required fields, dates, and capacity. | `InputValidator` |
| FR-03 | Register a student for an active event. | `EventService.registerStudent` |
| FR-04 | Reject duplicate registrations and full events. | Registration business rules |
| FR-05 | Cancel a student registration. | `EventService.cancelRegistration` |
| FR-06 | Search by title, category, or date. | `EventService.search` |
| FR-07 | Display occupancy and category reports. | `ReportService` |
| FR-08 | Persist data between runs. | `CsvEventRepository` |

### Functional Modules

1. **Event Management:** creates, lists, updates, searches, and cancels events.
2. **Registration Management:** registers and cancels student participation while enforcing capacity and uniqueness.
3. **Reporting and Analytics:** calculates active events, total capacity, registrations, category totals, and event occupancy.

## 6. Non-Functional Requirements

- **Usability:** numbered menu options and readable success/error messages support a beginner-friendly workflow.
- **Reliability:** invalid values are rejected before they are saved.
- **Maintainability:** presentation, business logic, models, persistence, and reporting are separate classes.
- **Performance:** in-memory collections provide immediate operations for normal campus-sized data.
- **Security:** required values are validated and CSV fields are escaped before storage.
- **Portability:** the application uses only the Java standard library and runs on Java 17 or later.
- **Error handling:** expected input and business errors are caught by the menu and displayed without terminating the program.

## 7. System Architecture

CampusPulse follows a simple layered architecture:

```text
User
  |
ConsoleMenu (presentation)
  |
EventService (business rules)
  |-------------------|
Domain Models      ReportService
  |
EventRepository (storage abstraction)
  |
CsvEventRepository
  |
CSV files
```

The `EventRepository` interface isolates business logic from the storage format. The current implementation is CSV-based, but the interface allows a database implementation to be added later without rewriting the service layer.

The full Mermaid architecture diagram, workflow diagram, use case diagram, class diagram, sequence diagram, and storage design are available in [docs/design.md](docs/design.md).

## 8. Design Diagrams

The project includes the following diagrams in `docs/design.md`:

- System architecture diagram
- Process flow/workflow diagram
- Use case diagram
- Class diagram
- Registration sequence diagram
- CSV storage schema design

The main workflow is:

```text
Start -> Load CSV data -> Select menu action
  -> Validate input and business rules
  -> Save valid event/registration
  -> Display result or error
  -> Return to menu
  -> Exit
```

## 9. Design Decisions and Rationale

### Object-Oriented Domain Model

`Event` represents an event and `Registration` represents a student's participation. Keeping domain data in classes makes the rules readable and supports future interface changes.

### Service Layer

`EventService` owns event and registration rules. This prevents the console layer from duplicating validation and ensures that every caller receives the same behavior.

### Repository Interface

`EventRepository` defines loading and saving operations. `CsvEventRepository` implements these operations using standard Java file APIs. This provides maintainability and makes the service independent of a specific storage technology.

### CSV Persistence

CSV was selected because it is transparent, portable, easy to inspect during evaluation, and requires no external database installation. Fields are escaped before writing.

### Java Date/Time API

`LocalDate` and `LocalDateTime` provide typed date handling and avoid ambiguous date formats.

## 10. Implementation Details

### Main Classes

| Class | Responsibility |
| --- | --- |
| `App` | Starts the application and connects dependencies. |
| `ConsoleMenu` | Reads commands and displays results. |
| `Event` | Stores event state and lifecycle. |
| `Registration` | Stores student registration data. |
| `EventRepository` | Defines persistence operations. |
| `CsvEventRepository` | Reads and writes CSV files. |
| `EventService` | Applies event and registration rules. |
| `ReportService` | Calculates participation summaries. |
| `InputValidator` | Reuses field and date validation. |
| `CampusPulseTest` | Runs automated behavior checks. |

### Input and Output

The user enters a menu number and values such as event title, category, ISO date, venue, capacity, event ID, and student details. The application prints created IDs, event lists, report totals, or a clear error message.

### Storage Files

- `data/events.csv`: event ID, title, category, date, venue, capacity, active status.
- `data/registrations.csv`: event ID, student ID, student name, registration timestamp.

## 11. Screenshots and Results

The application is a terminal application. A representative successful workflow is:

```text
=== CampusPulse Event Manager ===
Choose: 2
Title: Robotics Talk
Category: Technology
Date (YYYY-MM-DD): 2099-12-31
Venue: Auditorium
Capacity: 2
Created event cb3f5b2a

Choose: 6
--- CampusPulse Report ---
Active events: 1
Total capacity: 2
Total registrations: 0
Events by category: {Technology=1}
cb3f5b2a | Robotics Talk | 0/2 registered
```

For a visual submission, run the application, capture the terminal showing event creation and the report, and insert the screenshot here before exporting this report to PDF.

## 12. Testing Approach

The project contains a dependency-free test class, `CampusPulseTest`. It uses a temporary directory so tests do not modify the user's normal data.

The test suite verifies:

- Event creation
- Successful registration
- Duplicate registration rejection
- Capacity limit rejection
- Registration cancellation
- Event cancellation
- Rejection of registration for cancelled events
- Correct registration count after persistence operations

### Test Command

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src/main/java,src/test/java -Filter *.java).FullName
java -ea -cp out com.vityarthi.campuspulse.CampusPulseTest
```

### Test Result

```text
All CampusPulse tests passed.
```

## 13. Challenges Faced

The main challenge was keeping validation consistent across event creation, registration, cancellation, and persistence. Another challenge was selecting a storage format that demonstrates file handling without requiring a database server.

The repository abstraction solved the storage coupling problem, while the service layer gave all menu actions a single location for business rules.

## 14. Learnings and Key Takeaways

- Java classes are most useful when each class has a clear responsibility.
- Interfaces make implementation details replaceable.
- Validation belongs close to business rules, not only in the user interface.
- Collections simplify search, duplicate detection, grouping, and reporting.
- Automated tests make business rules easier to verify after changes.
- GitHub provides a traceable version-controlled project submission.

## 15. Future Enhancements

- Add a graphical or web user interface.
- Replace CSV files with SQLite or PostgreSQL.
- Add login and role-based permissions.
- Add attendance check-in and exportable reports.
- Add email reminders and calendar integration.
- Add support for multiple campuses and concurrent users.

## 16. References

1. Oracle Java Documentation: https://docs.oracle.com/en/java/
2. Java SE API Documentation: https://docs.oracle.com/en/java/javase/
3. Mermaid Diagram Documentation: https://mermaid.js.org/
