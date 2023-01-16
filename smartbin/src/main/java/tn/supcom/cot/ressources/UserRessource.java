package tn.supcom.cot.ressources;

import tn.supcom.cot.models.User;
import tn.supcom.cot.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRessource {
    private static final Supplier<WebApplicationException> NOT_FOUND =
            () -> new WebApplicationException(Response.Status.NOT_FOUND);

    @Inject
    UserRepository repository;

    @GET
    public List<User> findAll() {
        return repository.findAll()
                .collect(toList());
    }

    @GET
    @Path("/{id}")
    public User findById(@PathParam("id") String id) {
        return repository.findById(id).orElseThrow(NOT_FOUND);
    }

    @GET
    @Path("email/{email}")
    public List<User> findByEmail(@PathParam("email") String email) {
        return repository.findByEmail(email)
                .collect(toList());
    }

    @POST()
    @Path("/signup")
    public void save(User hero) {
        repository.save(hero);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") String id, User hero) {
        repository.save(hero);
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") String name) {
        repository.deleteById(name);
    }

}
