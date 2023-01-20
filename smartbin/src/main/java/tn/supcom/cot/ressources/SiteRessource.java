package tn.supcom.cot.ressources;

import jakarta.inject.Inject;
import tn.supcom.cot.models.Site;
import tn.supcom.cot.repositories.SiteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.function.Supplier;

@ApplicationScoped
@Path("/site")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SiteRessource {
    private static final Supplier<WebApplicationException> NOT_FOUND =
            () -> new WebApplicationException(Response.Status.NOT_FOUND);

    @Inject
    SiteRepository siterepository;

    @GET
    public List<Site> findAll() {
        return siterepository.findAll();
    }

    @GET
    @Path("/details/{id}")
    public Response findById(@PathParam("id") long id) {
        try {
            return Response.ok(siterepository.findById(id)).build();
        } catch (NotFoundException e) {
            return Response.status(400, "Lighting Module with id " + id + " NOT FOUND!").build();
        }
    }

    @POST
    public void save(Site site) {
        siterepository.saveSite(site);
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") long id) {
        siterepository.delete(id);
    }
}
