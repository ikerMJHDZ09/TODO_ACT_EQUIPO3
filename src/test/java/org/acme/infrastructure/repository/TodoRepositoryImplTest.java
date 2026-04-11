package org.acme.infrastructure.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.entities.TodoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class TodoRepositoryImplTest {

    @Inject
    TodoRepository todoRepository;

    @Test
    void debe_persistir_un_todo() {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID());
        todo.setTitle("Title H2");
        todo.setDescription("Description H2");
        todo.setCreatedAt(LocalDateTime.now());

        Todo saved = todoRepository.save(todo);

        assertNotNull(saved);
        assertEquals(todo.getId(), saved.getId());
        assertEquals("Title H2", saved.getTitle());
        assertEquals("Description H2", saved.getDescription());
        assertNotNull(saved.getCreatedAt());

        List<Todo> todos= todoRepository.findAllTodos();
        for (Todo todo2 : todos) {
            System.out.println(todo2);
        }
    }
}
