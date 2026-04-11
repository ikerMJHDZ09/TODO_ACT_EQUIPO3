package org.acme.infrastructure.mapper;

import org.acme.domain.models.Todo;
import org.acme.infrastructure.entities.TodoEntity;

public class TodoMapper {

    public static Todo toDomain(TodoEntity  entity){
        Todo todo = new Todo();
        todo.setId(entity.getId());
        todo.setTitle(entity.getTitle());
        todo.setDescription(entity.getDescription());
        todo.setCreatedAt(entity.getCreatedAt());
        return todo;
    }

    public static TodoEntity toEntity(Todo todo){
        TodoEntity entity = new TodoEntity();
        entity.setId(todo.getId());
        entity.setTitle(todo.getTitle());
        entity.setDescription(todo.getDescription());
        entity.setCreatedAt(todo.getCreatedAt());
        return entity;
    }
}
