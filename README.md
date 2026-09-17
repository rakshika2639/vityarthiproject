# CampusPulse

CampusPulse is an original Java console application for managing campus events and student participation. It applies object-oriented programming, collections, validation, file persistence, layered design, and reporting to a practical university workflow.

## Project Overview

Campus coordinators often track events and registrations across separate spreadsheets. CampusPulse provides one reliable workflow to create and manage events, register students, prevent invalid bookings, and view participation statistics.

## Features

- Create, list, update, and cancel campus events
- Register students for events with capacity and duplicate-registration checks
- Search events by category or date
- Generate attendance and participation reports
- Persist events and registrations in CSV files
- Validate all user input and provide actionable error messages
- Seed a demo dataset for a quick presentation

## Functional Modules

1. **Event Management:** event CRUD, search, capacity rules, and cancellation.
2. **Registration Management:** student registration, duplicate prevention, and cancellation.
3. **Reporting and Analytics:** event occupancy, category summaries, and student participation reports.

## Technologies

- Java 17+ (works with Java 26)
- Java Collections Framework
- Object-oriented design with services and repositories
- CSV file storage using the Java standard library
- Git and GitHub

## Project Structure

```text
src/
  main/java/com/vityarthi/campuspulse/
    App.java                 Application entry point
    ConsoleMenu.java         User interaction workflow
    Event.java               Event domain model
    Registration.java        Registration domain model
    EventRepository.java     Storage abstraction
    CsvEventRepository.java  CSV persistence implementation
    EventService.java        Event and registration business rules
    ReportService.java       Analytics calculations
    InputValidator.java      Reusable validation helpers
  test/java/com/vityarthi/campuspulse/
    CampusPulseTest.java     Self-contained validation tests
docs/design.md               Architecture, workflow, UML, and storage diagrams
statement.md                 Problem statement and scope
run.bat                      Windows compile and run script
run.sh                       macOS/Linux compile and run script
```

## Installation and Run

Prerequisite: Java 17 or later on `PATH`.

### Windows PowerShell

```powershell
.\run.bat
```

Or manually:

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
java -cp out com.vityarthi.campuspulse.App
```

### macOS/Linux

```bash
chmod +x run.sh
./run.sh
```

The application creates `data/events.csv` and `data/registrations.csv` on first run. These runtime files are intentionally ignored by Git.

## Testing

Run the built-in tests:

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src/main/java,src/test/java -Filter *.java).FullName
java -cp out com.vityarthi.campuspulse.CampusPulseTest
```

The tests cover event creation, duplicate registration prevention, capacity limits, cancellation, and report totals.

## Academic Alignment

The complete problem statement is in [statement.md](statement.md). Design artefacts and rationale are in [docs/design.md](docs/design.md). These documents cover the requirements, architecture, process flow, use cases, class diagram, sequence diagram, storage schema, non-functional requirements, testing approach, challenges, learnings, future enhancements, and references.
