package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateTodoDto;
import org.acme.application.usecase.CreateTodoUseCase;
import org.acme.application.usecase.DeleteTodoUseCase;
import org.acme.domain.models.Todo;

import java.util.UUID;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;

    @Inject
    public TodoResource(CreateTodoUseCase createTodoUseCase, DeleteTodoUseCase deleteTodoUseCase) {
        this.createTodoUseCase = createTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
    }

    @POST
    public Response createTodo(CreateTodoDto todoDto) {
        Todo todo = createTodoUseCase.execute(todoDto);
        return Response.ok(todo).build();
    }

    @DELETE
    @Path("/{id}")
<<<<<<< HEAD
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        // 🐛 BUG INTENCIONAL: ignoramos el id recibido
        // y siempre borramos el ToDo con id = 1
        boolean deleted = repo.deleteById(1L);
        return deleted
                ? Response.noContent().build()
                : Response.status(404).build();
=======
    public Response deleteTodo(@PathParam("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            boolean deleted = deleteTodoUseCase.execute(uuid);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Todo with id " + id + " not found\"}")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid UUID format\"}")
                    .build();
        }
>>>>>>> bugfix/delete-todo-wrong-id
    }
}
