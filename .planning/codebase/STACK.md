# Technology Stack

**Analysis Date:** 2025-02-14

## Languages

**Primary:**
- Java 17 - Entire application source code in `src/main/java`.

## Runtime

**Environment:**
- Quarkus 3.31.3

**Package Manager:**
- Maven 3.x (with `mvnw` wrapper)
- Lockfile: `pom.xml` (standard Maven)

## Frameworks

**Core:**
- Quarkus - Enterprise Java framework for microservices.
- Hibernate Panache - Simplified JPA persistence.
- RESTEasy Reactive / JSON-B - RESTful API and JSON serialization.
- ArC - Quarkus Dependency Injection (CDI based).

**Testing:**
- JUnit 5 - Unit and integration testing.
- REST Assured - API testing.

**Build/Dev:**
- Quarkus Maven Plugin - Development mode and native compilation.

## Key Dependencies

**Critical:**
- `com.google.firebase:firebase-admin:9.8.0` - Used for Firebase Authentication and User management in `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java` and `src/main/java/org/acme/application/usecase/RegisterUserUseCase.java`.

**Infrastructure:**
- `io.quarkus:quarkus-jdbc-mysql` - MySQL database driver.
- `io.quarkus:quarkus-hibernate-orm-panache` - ORM framework for data persistence.

## Configuration

**Environment:**
- Configured via `src/main/resources/application.properties`.
- Supports environment variables override via MicroProfile Config.

**Build:**
- `pom.xml` - Maven project descriptor.
- `src/main/docker/` - Multiple Dockerfiles for different deployment scenarios (JVM, Native, Micro).

## Platform Requirements

**Development:**
- JDK 17
- MySQL Database
- Maven

**Production:**
- JVM-based or Native-compiled Docker containers (see `src/main/docker/`).

---

*Stack analysis: 2025-02-14*
