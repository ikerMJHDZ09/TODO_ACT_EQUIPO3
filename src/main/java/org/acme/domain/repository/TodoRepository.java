package org.acme.domain.repository;

import org.acme.domain.models.Todo;

import java.util.List;

public interface TodoRepository {
    Todo save(Todo todo);
    List<Todo> findAllTodos();
}
