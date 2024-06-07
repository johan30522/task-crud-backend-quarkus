package org.jap.core.mapper;

import org.jap.api.dto.TaskCreateRequest;
import org.jap.api.dto.TaskResponse;
import org.jap.api.dto.TaskUpdateRequest;
import org.jap.infrastructure.entity.Task;

import java.util.List;

public interface TaskMapper {
    Task toEntity(TaskCreateRequest request);
    void updateEntity(TaskUpdateRequest request,Task task);
    TaskResponse toResponse(Task task);
    List<TaskResponse> toResponses(List<Task> list);
}
