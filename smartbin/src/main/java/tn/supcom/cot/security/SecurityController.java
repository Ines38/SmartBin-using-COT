package tn.supcom.cot.security;

import tn.supcom.cot.models.RoleDTO;
import tn.supcom.cot.models.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("security")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SecurityController {
    @Inject
    private SecurityService service;
    @Path("signup")
    @PermitAll
    @POST
    public void create(@Valid User user) {
        service.create(user);
    }




    @DELETE
    @Path("user/{id}")
    @RolesAllowed("Administrateur")
    public void delete(@PathParam("id") String id) {
        service.delete(id);
    }

    @Path("{id}")
    @PUT
    public void changePassword(@PathParam("id") String id, @Valid User dto) {
        service.updatePassword(id, dto);
    }

    @Path("roles/{id}")
    @PUT
    @RolesAllowed("ADMIN")
    public void addRole(@PathParam("id") String id, RoleDTO dto){
        service.addRole(id, dto);
    }

    @Path("roles/{id}")
    @DELETE
    @RolesAllowed("Administrateur")
    public void removeRole(@PathParam("id") String id, RoleDTO dto){
        service.removeRole(id, dto);
    }




    @DELETE
    @RolesAllowed("Administrateur")
    @Path("delete/{id}")
    public void removeUser(@PathParam("id") String id) {
        service.removeUser(id);
    }


    @DELETE
    @PermitAll
    @Path("token/{token}")
    public void removeToken(@PathParam("token") String token) {
        service.removeToken(token);
    }
}
