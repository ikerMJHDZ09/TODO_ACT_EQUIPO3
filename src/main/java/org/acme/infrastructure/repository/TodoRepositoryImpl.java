package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.models.Todo;
import org.acme.domain.repository.TodoRepository;
import org.acme.infrastructure.entities.TodoEntity;
import org.acme.infrastructure.mapper.TodoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TodoRepositoryImpl implements TodoRepository, PanacheRepositoryBase<TodoEntity, UUID> {

    @Override
    @Transactional
    public Todo save(Todo todo) {
        System.out.println("Entra al metodo save del repository");
        TodoEntity entity= TodoMapper.toEntity(todo);
        persist(entity);
        return TodoMapper.toDomain(entity);
    }

    @Override
    public List<Todo> findAllTodos() {
        List<TodoEntity> entities= findAll().stream().toList();
        List<Todo> response= new ArrayList<>();
        for (TodoEntity entity: entities) {
            response.add(TodoMapper.toDomain(entity));
        }
        return response;
    }
}
