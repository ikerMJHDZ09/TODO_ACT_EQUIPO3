package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateTodoDto;
import org.acme.application.usecase.CreateTodoUseCase;
import org.acme.domain.models.Todo;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;

    @Inject
    public TodoResource(CreateTodoUseCase createTodoUseCase) {
        this.createTodoUseCase = createTodoUseCase;
    }

    @POST
    public Response createTodo(CreateTodoDto todoDto) {
        Todo todo = createTodoUseCase.execute(todoDto);
        return Response.ok(todo).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        // 🐛 BUG INTENCIONAL: ignoramos el id recibido
        // y siempre borramos el ToDo con id = 1
        boolean deleted = repo.deleteById(1L);
        return deleted
                ? Response.noContent().build()
                : Response.status(404).build();
    }
}
