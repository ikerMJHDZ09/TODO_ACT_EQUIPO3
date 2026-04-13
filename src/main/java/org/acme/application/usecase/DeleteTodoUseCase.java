package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.TodoRepository;

import java.util.UUID;

@ApplicationScoped
public class DeleteTodoUseCase {

    private final TodoRepository todoRepository;

    @Inject
    public DeleteTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public boolean execute(UUID id) {
        return todoRepository.deleteById(id);
    }
}
