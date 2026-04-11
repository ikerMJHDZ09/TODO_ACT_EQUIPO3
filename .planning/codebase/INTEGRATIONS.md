# External Integrations

**Analysis Date:** 2025-02-14

## APIs & External Services

**Authentication & User Management:**
- Firebase Admin SDK - Used for verifying user tokens and creating new users in Firebase.
  - SDK/Client: `com.google.firebase:firebase-admin`
  - Auth: Credentials file path configured in `firebase.credentials` property.

## Data Storage

**Databases:**
- MySQL
  - Connection: `quarkus.datasource.jdbc.url`
  - Client: Hibernate ORM with Panache (`quarkus-hibernate-orm-panache`)

**File Storage:**
- Local filesystem only: Used to store Firebase Admin SDK JSON credentials.

**Caching:**
- None detected.

## Authentication & Identity

**Auth Provider:**
- Firebase Authentication
  - Implementation: Validated via `src/main/java/org/acme/infrastructure/security/FirebaseAuthFilter.java` using `FirebaseAuth.verifyIdToken`.
  - User synchronization: Local user records in `UserEntity` are linked via `firebaseUuid`.

## Monitoring & Observability

**Error Tracking:**
- None detected beyond application logs.

**Logs:**
- Standard Quarkus/JBoss LogManager.

## CI/CD & Deployment

**Hosting:**
- Docker - Multiple `Dockerfile` versions in `src/main/docker/` support JVM and native image deployment.

**CI Pipeline:**
- None detected.

## Environment Configuration

**Required env vars:**
- `QUARKUS_DATASOURCE_JDBC_URL` (optional override)
- `QUARKUS_DATASOURCE_USERNAME` (optional override)
- `QUARKUS_DATASOURCE_PASSWORD` (optional override)
- `FIREBASE_CREDENTIALS` (path to service account JSON)

**Secrets location:**
- `src/main/resources/*.json` (Firebase credentials).
- `src/main/resources/application.properties` (contains database password).

## Webhooks & Callbacks

**Incoming:**
- None detected.

**Outgoing:**
- Calls to Firebase Auth API via Admin SDK.

---

*Integration audit: 2025-02-14*
