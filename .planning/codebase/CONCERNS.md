# Codebase Concerns

**Analysis Date:** 2025-02-14

## Tech Debt

**Manual Mapping:**
- Issue: Mapping between Domain models and Persistence entities is done manually in mapper classes.
- Files: `src/main/java/org/acme/infrastructure/mapper/TodoMapper.java`, `src/main/java/org/acme/infrastructure/mapper/UserMapper.java`
- Impact: Increased boilerplate and potential for errors as models evolve.
- Fix approach: Use a mapping library like MapStruct.

**Logging Anti-Pattern:**
- Issue: Use of `System.out.println` instead of a proper logging framework.
- Files: `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java`, `src/main/java/org/acme/application/usecase/CreateTodoUseCase.java`, `src/main/java/org/acme/interfaces/rest/UserResource.java`
- Impact: Inefficient, hard to manage in production, and doesn't follow standard practices.
- Fix approach: Inject and use `org.jboss.logging.Logger`.

**Stale Test Code:**
- Issue: Tests for a non-existent `GreetingResource` are present.
- Files: `src/test/java/org/acme/GreetingResourceTest.java`, `src/test/java/org/acme/GreetingResourceIT.java`
- Impact: Confusing for new developers and potentially fails in CI if they rely on deleted code.
- Fix approach: Remove stale tests and replace them with tests for existing resources.

## Security Considerations

**Hardcoded Secrets in Configuration:**
- Issue: Database password and reference to Firebase service account key are hardcoded in `application.properties`.
- Files: `src/main/resources/application.properties`
- Risk: Potential for credential leak if committed or accessed by unauthorized users.
- Current mitigation: The Firebase JSON file is listed in `.gitignore`.
- Recommendations: Use environment variables or a secrets manager for sensitive configuration.

## Fragile Areas

**Authentication Filter Path Bypassing:**
- Issue: The authentication filter uses hardcoded string equality to bypass specific paths (e.g., `/users`).
- Files: `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java`
- Why fragile: Subtle changes in URL patterns (e.g., trailing slashes) or addition of new endpoints under `/users` could lead to unintended security holes or `NullPointerException` if `AuthContext` is not populated.
- Safe modification: Use a more robust path matching strategy (e.g., regex or framework-level security) and handle `AuthContext` state more explicitly.

## Test Coverage Gaps

**Missing Business Logic and Resource Tests:**
- What's not tested: UseCases, Repositories, and REST Resources for Todo and User functionality.
- Files: `src/main/java/org/acme/application/usecase/`, `src/main/java/org/acme/interfaces/rest/`, `src/main/java/org/acme/infrastructure/repository/`
- Risk: Regressions in core business logic or API contracts could go unnoticed.
- Priority: High

## Error Handling Gaps

**Generic Exception Handling:**
- Issue: REST resources catch generic `Exception` and return the message directly in the response.
- Files: `src/main/java/org/acme/interfaces/rest/UserResource.java`
- Risk: Leaking internal stack traces or database details to clients.
- Priority: Medium

---

*Concerns audit: 2025-02-14*
