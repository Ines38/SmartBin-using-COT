package tn.supcom.cot.ressources;

import jakarta.inject.Inject;
import tn.supcom.cot.models.Box;
import tn.supcom.cot.models.User;
import tn.supcom.cot.repositories.BoxRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Supplier;


@ApplicationScoped
@Path("/box")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoxRessource {
    private static final Supplier<WebApplicationException> NOT_FOUND =
            () -> new WebApplicationException(Response.Status.NOT_FOUND);

    @Inject
    BoxRepository boxrepository;

    @GET
    public List<Box> findAll() {
        return boxrepository.findAll();
    }

    @GET
    @Path("/details/{id}")
    public Response findById(@PathParam("id") long id) {
        try {
            return Response.ok(boxrepository.findById(id)).build();
        } catch (NotFoundException e) {
            return Response.status(400, "Lighting Module with id " + id + " NOT FOUND!").build();
        }
    }

    @POST
    public void save(Box box) {
        boxrepository.saveBox(box);
    }
}