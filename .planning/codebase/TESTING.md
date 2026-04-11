# Testing Patterns

**Analysis Date:** 2025-02-17

## Test Framework

**Runner:**
- JUnit 5 (JUnit Jupiter)
- `quarkus-junit`

**Assertion Library:**
- `RestAssured` for API testing.
- `Hamcrest` for assertions in `RestAssured` chains.

**Run Commands:**
```bash
./mvnw test              # Run unit and integration tests using Surefire
./mvnw verify            # Run all tests, including integration tests via Failsafe
```

## Test File Organization

**Location:**
- Separate: `src/test/java/org/acme/`

**Naming:**
- Unit tests: `*Test.java` (e.g., `GreetingResourceTest.java`)
- Integration tests: `*IT.java` (e.g., `GreetingResourceIT.java`)

**Structure:**
```
src/test/java/org/acme/
├── GreetingResourceTest.java
└── GreetingResourceIT.java
```

## Test Structure

**Suite Organization:**
```java
@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }
}
```

**Patterns:**
- `@QuarkusTest`: Boots the Quarkus environment for the test.
- `RestAssured.given()`: Initiates an API test request.

## Mocking

**Framework:** None explicitly configured in the provided files beyond standard Quarkus test tools.

**Patterns:**
- No mocking patterns were observed in the existing tests (scaffolded tests are basic).

## Fixtures and Factories

**Test Data:**
- None observed in existing tests.

**Location:**
- [Not applicable]

## Coverage

**Requirements:** [None enforced]

**View Coverage:**
- No coverage plugin (e.g., JaCoCo) configured in `pom.xml`.

## Test Types

**Unit Tests:**
- Basic scaffolding tests for the (now removed) GreetingResource.

**Integration Tests:**
- `@QuarkusIntegrationTest` used in `GreetingResourceIT.java` to test against the packaged application.

**E2E Tests:**
- [Not used]

## Common Patterns

**Async Testing:**
- [Not applicable] - No async tests found.

**Error Testing:**
- [Not applicable] - No error tests found.

---

*Testing analysis: 2025-02-17*
