package org.jap.api.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jap.api.dto.ErrorResponse;
import org.jap.api.dto.ImageCreateRequest;
import org.jap.api.dto.ImageResponse;
import org.jap.core.annotation.Secured;
import org.jap.core.service.ImageService;
import org.jap.core.service.ValidatorService;

import java.util.List;
import java.util.Optional;

@Path("/api/image")
@ApplicationScoped
public class ImageResource {

    private ImageService imageService;
    private ValidatorService validatorService;

    @Inject
    public ImageResource(ImageService imageService, ValidatorService validatorService) {
        this.imageService = imageService;
        this.validatorService = validatorService;
    }

    @POST
    @Transactional
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addImage(@Context SecurityContext securityContext,ImageCreateRequest request) {
        ErrorResponse errorResponse = validatorService.validatorDto(request);
        if (errorResponse != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        ImageResponse imageResponse = imageService.save(request);
        return Response.ok(imageResponse).build();
    }

    @GET
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImage(@Context SecurityContext securityContext,@PathParam("id") Long id) {
        Optional<ImageResponse> image = imageService.getByTaskId(id);
        if (image.isPresent()) {
            return Response.ok(image.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Image not found", "", List.of())).build();
        }
    }

    @GET
    @Secured
    @Path("/task/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImageByTaskId(@Context SecurityContext securityContext,@PathParam("taskId") Long taskId) {
        Optional<ImageResponse> image = imageService.getByTaskId(taskId);
        if (image.isPresent()) {
            return Response.ok(image.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Image not found for task with id: " + taskId, "", List.of())).build();
        }
    }

    @PUT
    @Secured
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateImage(@Context SecurityContext securityContext,@PathParam("id") Long id, ImageCreateRequest request) {
        ErrorResponse errorResponse = validatorService.validatorDto(request);
        if (errorResponse != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        ImageResponse imageResponse = imageService.update(id, request);
        return Response.ok(imageResponse).build();
    }

    @DELETE
    @Secured
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage(@Context SecurityContext securityContext,@PathParam("id") Long id) {
        boolean deleted = imageService.delete(id);
        if (deleted) {
            return Response.ok("Image deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Image not found", "", List.of())).build();
        }
    }
}
