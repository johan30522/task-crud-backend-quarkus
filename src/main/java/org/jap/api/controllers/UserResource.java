package org.jap.api.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jap.api.dto.*;
import org.jap.core.service.UserService;
import org.jap.core.service.ValidatorService;

@Path("/api/user")
public class UserResource {
    private UserService userService;
    private ValidatorService validatorService;

    @Inject
    public UserResource(UserService userService, ValidatorService validatorService) {
        this.userService = userService;
        this.validatorService = validatorService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(userService.getAllUsers()).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id,UserUpdateRequest userUpdateRequest){
        ErrorResponse errorResponse = validatorService.validatorDto(userUpdateRequest);
        if (errorResponse!=null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        UserResponse userResponse= userService.updateUser(id, userUpdateRequest);
        return Response.ok(userResponse).build();
    }



}
