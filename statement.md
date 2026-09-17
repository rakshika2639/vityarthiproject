# Project Statement: CampusPulse

## Problem Statement

Campus event coordinators need a simple way to publish events, manage limited seats, and understand participation. Manual lists make duplicate registrations, overbooking, and incomplete attendance reports likely. CampusPulse solves this problem through a validated, persistent Java application.

## Scope

The project covers event creation and maintenance, student registration and cancellation, capacity enforcement, CSV storage, and participation reporting. It is intentionally a local single-user application; authentication, online payments, and multi-campus synchronization are outside the current scope.

## Target Users

- Student activity coordinators
- Faculty event organizers
- Student representatives
- Students browsing and joining campus activities

## High-Level Features

- Manage event details including title, category, date, venue, and capacity
- Register a student using a unique student ID
- Reject duplicate registrations and full events
- Cancel events and registrations safely
- Search and report on event participation

## Objectives

1. Apply Java object-oriented programming to a real-world problem.
2. Demonstrate separation of domain models, services, storage, and presentation.
3. Provide clear input/output behavior with validation and error handling.
4. Produce useful participation metrics for decision-making.
5. Maintain a documented, testable, version-controlled project.

## Functional Requirements

| ID | Requirement |
| --- | --- |
| FR-01 | The coordinator can create, view, update, and cancel events. |
| FR-02 | The system validates event dates, capacities, and required fields. |
| FR-03 | A student can register for an active event. |
| FR-04 | The system prevents duplicate registrations and over-capacity bookings. |
| FR-05 | A registration can be cancelled without deleting the event. |
| FR-06 | The user can search events by category or date. |
| FR-07 | The system generates occupancy and participation reports. |
| FR-08 | Events and registrations persist between application runs. |

## Non-Functional Requirements

- **Usability:** menus use numbered actions and explain validation errors.
- **Reliability:** invalid records are rejected before they reach storage.
- **Maintainability:** business logic is separated into small classes with single responsibilities.
- **Performance:** in-memory collections make normal campus-sized searches and reports immediate.
- **Security:** user input is validated and CSV fields are escaped before persistence.
- **Portability:** only the Java standard library is required.
