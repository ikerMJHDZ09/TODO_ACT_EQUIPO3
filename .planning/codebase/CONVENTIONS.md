# Coding Conventions

**Analysis Date:** 2025-02-17

## Naming Patterns

**Files:**
- [PascalCase]: `TodoResource.java`, `CreateTodoUseCase.java`

**Functions:**
- [camelCase]: `execute(CreateTodoDto todoDto)`, `createTodo(CreateTodoDto todoDto)`

**Variables:**
- [camelCase]: `todoRepository`, `authContext`, `decodedToken`

**Types:**
- [PascalCase]: `Todo`, `TodoEntity`, `CreateTodoDto`, `TodoRepository`

## Code Style

**Formatting:**
- [Tool used]: Standard Java conventions, no specific formatting plugin (like Spotless) configured in `pom.xml`.
- [Key settings]: Indentation 4 spaces.

**Linting:**
- [Tool used]: No specific linting tool (like Checkstyle) configured in `pom.xml`.

## Import Organization

**Order:**
1. `jakarta.*` imports first
2. Framework-specific imports (e.g., `io.quarkus.*`, `com.google.firebase.*`)
3. Project-specific imports (e.g., `org.acme.*`)

**Path Aliases:**
- [Not applicable]: Standard Java package structure.

## Error Handling

**Patterns:**
- Try-catch in Resource methods returning `Response.serverError().entity(e.getMessage()).build()` in `src/main/java/org/acme/interfaces/rest/UserResource.java`.
- Explicit `throws` declarations in UseCases, e.g., `execute(RegisterUserDto registerUserDto) throws FirebaseAuthException`.
- Aborting requests in Filters using `requestContext.abortWith()` in `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java`.

## Logging

**Framework:** [console]
- Uses `System.out.println` for logging/debugging in several places, e.g., `CreateTodoUseCase.java`, `UserResource.java`, `FirebaseAuthFilter.java`.
- No logging framework (like SLF4J/JBoss LogManager) used explicitly in the code.

## Comments

**When to Comment:**
- Comments are mostly absent.
- Some code is commented out (e.g., scaffolding comments in `GreetingResourceIT.java`).

**JSDoc/TSDoc:**
- [Not applicable]: Java projects use Javadoc, but none were observed in the code.

## Function Design

**Size:** Generally small, focusing on a single responsibility (e.g., UseCase `execute` method).

**Parameters:**
- Use DTOs for multi-parameter inputs in REST resources and UseCases, e.g., `CreateTodoDto`, `RegisterUserDto`.

**Return Values:**
- Use domain models (`Todo`, `User`) as return types in UseCases.
- Use JAX-RS `Response` objects in REST Resources.

## Module Design

**Exports:** Public classes and methods are used throughout.

**Barrel Files:** [Not applicable] - Uses Java package structure.

---

*Convention analysis: 2025-02-17*
