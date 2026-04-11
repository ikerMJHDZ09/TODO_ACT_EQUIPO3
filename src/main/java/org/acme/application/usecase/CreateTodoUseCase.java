package org.acme.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.CreateTodoDto;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.security.AuthContext;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class CreateTodoUseCase {

    private final TodoRepository todoRepository;

    private final AuthContext authContext;

    @Inject
    public CreateTodoUseCase(TodoRepository todoRepository, AuthContext authContext) {
        this.todoRepository = todoRepository;
        this.authContext = authContext;
    }

    public Todo execute(CreateTodoDto todoDto) {
        System.out.println("Quien esta creando un todo es: "+ authContext.getUser().getId()+ " "+ authContext.getUser().getFullName());
        Todo todo=new Todo();

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setId(UUID.randomUUID());
        todo.setCreatedAt(LocalDateTime.now());
        todo = todoRepository.save(todo);
        System.out.println("Esta llegando al final de la logica de negocio");
        return todo;
    }
}
