# Codebase Structure

**Analysis Date:** 2025-02-14

## Directory Layout

```
src/
├── main/
│   ├── docker/                 # Containerization artifacts
│   ├── java/
│   │   └── org/acme/
│   │       ├── application/    # Application logic & orchestration
│   │       │   ├── dto/        # Data Transfer Objects
│   │       │   └── usecase/    # Core business processes
│   │       ├── domain/         # Core business domain definitions
│   │       │   ├── models/     # Domain POJO models
│   │       │   └── repository/ # Repository interfaces
│   │       ├── infrastructure/ # External implementations
│   │       │   ├── config/     # External service configuration
│   │       │   ├── entities/   # JPA persistence entities
│   │       │   ├── mapper/     # Logic for mapping between layers
│   │       │   ├── repository/ # Panache repository implementations
│   │       │   └── security/   # Authentication filters & context
│   │       └── interfaces/     # Entry point definitions
│   │           └── rest/       # JAX-RS REST resources
│   └── resources/              # Configuration files & static assets
└── test/                       # Automated tests
```

## Directory Purposes

**Application (`org.acme.application`):**
- Purpose: Defines and implements the application's use cases.
- Contains: Business logic orchestration and DTOs.
- Key files: `application/usecase/CreateTodoUseCase.java`, `application/dto/CreateTodoDto.java`.

**Domain (`org.acme.domain`):**
- Purpose: Represents the core business domain.
- Contains: Framework-independent POJOs and persistence contracts (interfaces).
- Key files: `domain/models/Todo.java`, `domain/repository/TodoRepository.java`.

**Infrastructure (`org.acme.infrastructure`):**
- Purpose: Implements the "outer layers" of the hexagonal architecture.
- Contains: Database entities, repository implementations, mappers, and security logic.
- Key files: `infrastructure/repository/TodoRepositoryImpl.java`, `infrastructure/entities/TodoEntity.java`, `infrastructure/security/FirebaseAuthFilter.java`.

**Interfaces (`org.acme.interfaces`):**
- Purpose: Exposes the application to the external world.
- Contains: RESTful web resources.
- Key files: `interfaces/rest/TodoResource.java`, `interfaces/rest/UserResource.java`.

## Key File Locations

**Entry Points:**
- `src/main/java/org/acme/interfaces/rest/`: JAX-RS REST endpoints.

**Configuration:**
- `src/main/resources/application.properties`: Main application configuration.
- `src/main/java/org/acme/infrastructure/config/FirebaseConfig.java`: Firebase SDK setup.

**Core Logic:**
- `src/main/java/org/acme/application/usecase/`: Implementation of business processes.

**Testing:**
- `src/test/java/org/acme/`: Integration and unit tests.

## Naming Conventions

**Files:**
- Use Cases: `[Action]UseCase.java` (e.g., `CreateTodoUseCase.java`)
- DTOs: `[Description]Dto.java` (e.g., `CreateTodoDto.java`)
- Persistence Entities: `[Model]Entity.java` (e.g., `TodoEntity.java`)
- Repositories: `[Model]RepositoryImpl.java` for implementations.

**Directories:**
- Pluralization: Generally used for directory names where appropriate (e.g., `models`, `entities`, `usecase`, `dto`).

## Where to Add New Code

**New Feature:**
- Primary code: Implementation goes in a new `UseCase` in `src/main/java/org/acme/application/usecase/`.
- Tests: Add a new test class in `src/test/java/org/acme/`.

**New Component/Module:**
- Implementation: Register a new Resource in `src/main/java/org/acme/interfaces/rest/`.

**Utilities:**
- Shared helpers: Should be placed in a new `util` package if they are general, or in `infrastructure` if they relate to external integrations.

## Special Directories

**docker:**
- Purpose: Contains various Dockerfiles for JVM and Native builds.
- Generated: No
- Committed: Yes

**resources:**
- Purpose: Contains sensitive configuration files (Firebase credentials) and application properties.
- Generated: No
- Committed: Yes (Note: Ensure credentials are secure).

---

*Structure analysis: 2025-02-14*
