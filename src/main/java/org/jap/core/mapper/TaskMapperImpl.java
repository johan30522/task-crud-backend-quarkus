package org.jap.core.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.jap.api.dto.ImageResponse;
import org.jap.api.dto.TaskCreateRequest;
import org.jap.api.dto.TaskResponse;
import org.jap.api.dto.TaskUpdateRequest;
import org.jap.infrastructure.entity.Image;
import org.jap.infrastructure.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskMapperImpl implements TaskMapper {


    @Override
    public Task toEntity(TaskCreateRequest request) {
        if (request ==null){
            return null;
        }
        Task task = new Task();
        task.setName(request.name());
        task.setStatus(request.status());
        task.setImageUrl(request.imageUrl());
        return task;
    }

    @Override
    public void updateEntity(TaskUpdateRequest request, Task task) {

        if (request== null || task==null){
            return;
        }

        task.setImageUrl(request.imageUrl());
        task.setName(request.name());
        task.setStatus(request.status());
    }

    @Override
    public TaskResponse toResponse(Task task) {
        return  new TaskResponse(task.getId(), task.getName(), task.getStatus(), task.getImageUrl(),toImageResponse(task.getImage()));

    }

    @Override
    public List<TaskResponse> toResponses(List<Task> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ImageResponse toImageResponse(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageResponse(
                image.getId(),
                image.getName(),
                image.getImageUrl(),
                image.getImageId(),
                image.getTask() != null ? image.getTask().getId() : null
        );
    }
}
