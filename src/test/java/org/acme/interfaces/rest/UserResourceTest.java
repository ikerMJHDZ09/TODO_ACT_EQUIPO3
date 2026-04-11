package org.acme.interfaces.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;
import org.acme.infrastructure.firebase.FirebaseUserCreator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class UserResourceTest {

    @InjectMock
    FirebaseUserCreator firebaseUserCreator;

    @Inject
    UserRepository userRepository;

    @Test
    void debe_registrar_usuario_exitosamente() throws FirebaseAuthException {
        String firebaseUid = "firebase-uid-" + UUID.randomUUID();
        String email = "alice-" + UUID.randomUUID() + "@test.com";

        UserRecord firebaseRecord = Mockito.mock(UserRecord.class);
        Mockito.when(firebaseRecord.getUid()).thenReturn(firebaseUid);
        Mockito.when(firebaseUserCreator.create(Mockito.eq(email), Mockito.eq("Supersecret123")))
                .thenReturn(firebaseRecord);

        String body = "{\"fullName\":\"Alice Smith\",\"email\":\"" + email + "\",\"password\":\"Supersecret123\",\"passwordConfirm\":\"Supersecret123\"}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(200)
                .body("email", equalTo(email))
                .body("fullName", equalTo("Alice Smith"))
                .body("role", equalTo("USER"))
                .body("active", equalTo(true))
                .body("firebaseUuid", equalTo(firebaseUid))
                .body("id", notNullValue());

        // Verify the user was actually persisted in H2
        Optional<User> persisted = userRepository.findByFirebaseUuid(firebaseUid);
        assertTrue(persisted.isPresent(), "User should be persisted in H2");
        assertEquals(email, persisted.get().getEmail());
        assertEquals("Alice Smith", persisted.get().getFullName());
    }

    @Test
    void debe_retornar_400_cuando_las_contraseñas_no_coinciden() {
        String body = "{\"fullName\":\"Alice Smith\",\"email\":\"alice@test.com\",\"password\":\"Supersecret123\",\"passwordConfirm\":\"WrongSecret123\"}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400)
                .body(equalTo("Las contraseñas no coinciden"));
    }

    @Test
    void debe_retornar_400_cuando_el_email_es_invalido() {
        String body = "{\"fullName\":\"Alice Smith\",\"email\":\"invalid-email\",\"password\":\"Supersecret123\",\"passwordConfirm\":\"Supersecret123\"}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    void debe_retornar_400_cuando_la_contraseña_es_muy_corta() {
        String body = "{\"fullName\":\"Alice Smith\",\"email\":\"alice@test.com\",\"password\":\"Short1\",\"passwordConfirm\":\"Short1\"}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    void debe_retornar_400_cuando_falta_el_nombre_completo() {
        String body = "{\"email\":\"alice@test.com\",\"password\":\"Supersecret123\",\"passwordConfirm\":\"Supersecret123\"}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    void debe_retornar_400_cuando_la_contraseña_no_tiene_mayusculas() {
        String body = "{\"fullName\":\"Alice Smith\",\"email\":\"alice@test.com\",\"password\":\"supersecret123\",\"passwordConfirm\":\"supersecret123\"}";

        given()
                .contentType(JSON)
                .body(body)
                .when().post("/users")
                .then()
                .statusCode(400);
    }
}
