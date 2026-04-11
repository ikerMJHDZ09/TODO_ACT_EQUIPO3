# Architecture

**Analysis Date:** 2025-02-14

## Pattern Overview

**Overall:** Clean Architecture / Hexagonal Architecture

**Key Characteristics:**
- **Layered Separation:** Decouples core business logic from infrastructure and external interfaces.
- **Dependency Inversion:** Domain layer defines repository interfaces that the infrastructure layer implements.
- **Domain-Driven Design (DDD) Lite:** Core business entities are pure POJOs without framework annotations.
- **Stateless Services:** Use cases and resources are stateless, with state managed via persistence in the infrastructure layer.

## Layers

**Domain:**
- Purpose: Core business logic and model definitions.
- Location: `src/main/java/org/acme/domain/`
- Contains: Domain models (`models/`) and repository interfaces (`repository/`).
- Depends on: None.
- Used by: Application, Infrastructure.

**Application:**
- Purpose: Orchestrates business logic and processes.
- Location: `src/main/java/org/acme/application/`
- Contains: Use cases (`usecase/`) and Data Transfer Objects (`dto/`).
- Depends on: Domain.
- Used by: Interfaces.

**Infrastructure:**
- Purpose: Implements external concerns like database persistence, security, and external services.
- Location: `src/main/java/org/acme/infrastructure/`
- Contains: JPA entities (`entities/`), repository implementations (`repository/`), object mappers (`mapper/`), security filters (`security/`), and configuration (`config/`).
- Depends on: Domain.
- Used by: Application (via DI).

**Interfaces:**
- Purpose: Entry points to the application for external clients.
- Location: `src/main/java/org/acme/interfaces/`
- Contains: REST resources (`rest/`) using JAX-RS.
- Depends on: Application, Domain.
- Used by: External API clients.

## Data Flow

**Request Flow:**

1.  **Entry:** A JAX-RS Resource in `src/main/java/org/acme/interfaces/rest/` receives an HTTP request.
2.  **DTO Mapping:** Quarkus/Jackson automatically maps the request payload to a DTO in `src/main/java/org/acme/application/dto/`.
3.  **UseCase Orchestration:** The Resource calls an `execute` method on a UseCase in `src/main/java/org/acme/application/usecase/`.
4.  **Domain Interaction:** The UseCase interacts with Domain models (`src/main/java/org/acme/domain/models/`) and calls a Repository interface (`src/main/java/org/acme/domain/repository/`).
5.  **Persistence:** The Infrastructure implementation of the repository (`src/main/java/org/acme/infrastructure/repository/`) uses Panache/Hibernate to perform DB operations.
6.  **Response Mapping:** Domain models are returned from the UseCase and eventually sent as JSON in the HTTP response by the Resource.

**State Management:**
- Persistence is handled by Hibernate ORM with Panache.
- Transactional integrity is managed using `@Transactional` annotations on repository implementations (e.g., `src/main/java/org/acme/infrastructure/repository/TodoRepositoryImpl.java`).

## Key Abstractions

**UseCase:**
- Purpose: Represents a specific business action or process.
- Examples: `src/main/java/org/acme/application/usecase/CreateTodoUseCase.java`, `src/main/java/org/acme/application/usecase/RegisterUserUseCase.java`
- Pattern: Command/Strategy pattern.

**Repository Interface:**
- Purpose: Defines the contract for data persistence in the Domain layer.
- Examples: `src/main/java/org/acme/domain/repository/TodoRepository.java`, `src/main/java/org/acme/domain/repository/UserRepository.java`

**Panache Repository:**
- Purpose: Concrete implementation of data access logic using Quarkus Panache.
- Examples: `src/main/java/org/acme/infrastructure/repository/TodoRepositoryImpl.java`

## Entry Points

**REST Resources:**
- Location: `src/main/java/org/acme/interfaces/rest/`
- Triggers: HTTP Requests.
- Responsibilities: Routing, Request validation (via DTOs), and calling Application UseCases.

## Error Handling

**Strategy:** Container-level authentication error handling and explicit response building.

**Patterns:**
- **Security Filter Interruption:** `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java` aborts the request context if authentication fails.

## Cross-Cutting Concerns

**Logging:** Basic `System.out.println` used for observability during development.
**Validation:** Implicit via DTO mapping.
**Authentication:** Firebase-based authentication implemented in `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java`.
**Security Context:** `src/main/java/org/acme/infrastructure/security/AuthContext.java` provides access to the authenticated user within the request scope.

---

*Architecture analysis: 2025-02-14*
