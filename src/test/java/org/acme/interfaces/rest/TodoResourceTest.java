package org.acme.interfaces.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.acme.infrastructure.entities.TodoEntity;
import org.acme.infrastructure.repository.TodoRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class TodoResourceTest {

    @Inject
    TodoRepositoryImpl todoRepository;

    @Test
    void debe_crear_todo_en_h2_y_retornarlo() {
        String body = "{\"title\":\"Read a book\",\"description\":\"Clean Architecture\"}";

        Response response = given()
                .contentType(JSON)
                .header("Authorization", "Bearer fake-token")
                .body(body)
                .when().post("/todos")
                .then()
                .statusCode(200)
                .body("title", equalTo("Read a book"))
                .body("description", equalTo("Clean Architecture"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue())
                .extract().response();

        UUID returnedId = UUID.fromString(response.jsonPath().getString("id"));

        TodoEntity persisted = todoRepository.findById(returnedId);
        assertNotNull(persisted, "Todo should be persisted in H2");
        assertEquals(returnedId, persisted.getId());
        assertEquals("Read a book", persisted.getTitle());
        assertEquals("Clean Architecture", persisted.getDescription());
        assertNotNull(persisted.getCreatedAt());
    }
}
