package org.jap.api.controllers;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jap.api.dto.*;
import org.jap.core.annotation.Secured;
import org.jap.core.service.TaskService;
import org.jap.core.service.ValidatorService;

import java.util.List;

@Path("/api/task")
@ApplicationScoped
public class TaskResource {


    private TaskService taskService;
    private ValidatorService validatorService;


    @Inject
    public TaskResource(TaskService taskService, ValidatorService validatorService) {
        this.taskService = taskService;
        this.validatorService = validatorService;
    }

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(@Context SecurityContext securityContext){
        String username = securityContext.getUserPrincipal().getName();
        if (username==null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","",List.of())).build();
        }
        List<TaskResponse> taskResponses = taskService.getAll(username);
        return Response.ok(taskResponses).build();
    }

    @POST
    @Secured
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //public Response addTask(@Context SecurityContext securityContext,TaskCreateRequest taskRequest){
    public Response addTask(@Context SecurityContext securityContext,TaskCreateRequest taskRequest){
        ErrorResponse errorResponse = validatorService.validatorDto(taskRequest);
        if (errorResponse!=null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        String username = securityContext.getUserPrincipal().getName();
        if (username==null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","",List.of())).build();
        }
        TaskResponse taskResponse= taskService.save(taskRequest,username);
        return Response.ok(taskResponse).build();
    }
    @PUT
    @Secured
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    //public Response addTask(@Context SecurityContext securityContext,TaskCreateRequest taskRequest){
    public Response updateTask(@Context SecurityContext securityContext, TaskUpdateRequest taskRequest, @PathParam("id") Long id){
        ErrorResponse errorResponse = validatorService.validatorDto(taskRequest);
        if (errorResponse!=null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        if (id==null || id==0){
            ValidationError validationError=new ValidationError("id","Task id is required");
            ErrorResponse errorResponseId = new ErrorResponse("Validation error","", List.of(validationError));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponseId).build();
        }
        String username = securityContext.getUserPrincipal().getName();
        if (username==null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","",List.of())).build();
        }
        TaskResponse taskResponse= taskService.updateTask(id,taskRequest,username);
        return Response.ok(taskResponse).build();
    }
    @DELETE
    @Secured
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@Context SecurityContext securityContext, @PathParam("id") Long id){
        if (id==null || id==0){
            ValidationError validationError=new ValidationError("id","Task id is required");
            ErrorResponse errorResponseId = new ErrorResponse("Validation error","", List.of(validationError));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponseId).build();
        }
        String username = securityContext.getUserPrincipal().getName();        if (username==null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse("User unauthorized","",List.of())).build();
        }
        boolean deleted= taskService.deleteTask(id,username);
        if(deleted){
            return Response.ok("Task deleted").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Task not found","",List.of())).build();
        }
    }



}
