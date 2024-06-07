package org.jap.api.controllers;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jap.api.dto.*;
import org.jap.core.annotation.Secured;
import org.jap.core.service.UserService;
import org.jap.core.service.ValidatorService;
import org.jap.core.util.JWTUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/api/auth")
public class AuthResource {

    private UserService userService;
    private ValidatorService validatorService;

    @Inject
    public AuthResource(UserService userService, ValidatorService validatorService) {
        this.userService = userService;
        this.validatorService =validatorService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserLoginRequest request){
        ErrorResponse errorResponse = validatorService.validatorDto(request);
        if (errorResponse!=null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        UserResponse userResponse=userService.validateUser(request.userName(), request.password());
        if(userResponse!=null) {
            String csrfToken = UUID.randomUUID().toString();
            String jwtToken = JWTUtil.generateToken(request.userName(), csrfToken);
            UserLoginResponse userLoginResponse=new UserLoginResponse(userResponse,"Bearer " + jwtToken,csrfToken);
            return Response.ok(userLoginResponse).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","", List.of())).build();
        }
    }

    @POST
    @Transactional
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserRegisterRequest userRegisterRequest) throws Exception {
        ErrorResponse errorResponse = validatorService.validatorDto(userRegisterRequest);
        if (errorResponse!=null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        UserResponse userResponse=userService.createUser(userRegisterRequest);
        return Response.ok(userResponse).build();
    }

    @GET
    @Path("/renew-token")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response renewToken(@Context SecurityContext securityContext){
        String username = securityContext.getUserPrincipal().getName();
        Optional<UserResponse> userResponse=userService.getUserByEmail(username);
        if(userResponse.isEmpty()){
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","", List.of())).build();
        }
        String csrfToken = UUID.randomUUID().toString();
        String jwtToken = JWTUtil.generateToken(username, csrfToken);
        UserLoginResponse userLoginResponse=new UserLoginResponse(userResponse.get(),"Bearer " + jwtToken,csrfToken);
        return Response.ok(userLoginResponse).build();
    }




}
