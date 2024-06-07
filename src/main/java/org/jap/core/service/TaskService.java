package org.jap.core.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jap.api.dto.TaskCreateRequest;
import org.jap.api.dto.TaskResponse;
import org.jap.api.dto.TaskUpdateRequest;
import org.jap.api.dto.UserResponse;
import org.jap.core.mapper.TaskMapper;
import org.jap.core.mapper.UserMapper;
import org.jap.infrastructure.entity.Task;
import org.jap.infrastructure.entity.User;
import org.jap.infrastructure.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private UserService userService;
    private UserMapper userMapper;

    @Inject
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserService userService,UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService=userService;
        this.userMapper=userMapper;
    }

    public TaskResponse save (TaskCreateRequest request,String email){

        Optional<UserResponse> userResponse =userService.getUserByEmail(email);

        if(userResponse.isEmpty()){
            throw new IllegalArgumentException("Task must be associated with a user");
        }
        Task task = taskMapper.toEntity(request);
        task.setUser(userMapper.toEntity(userResponse.get()));

        taskRepository.persist(task);
        return taskMapper.toResponse(task);
    }


    public List<TaskResponse> getAll(String email){
        Optional<UserResponse> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        List<Task> tasks = taskRepository.find("user.id", user.get().id()).list();
        return taskMapper.toResponses(tasks);
    }
    public Optional<TaskResponse> getById(Long userId, Long id){
        return taskRepository.findByIdOptional(id)
                .map(taskMapper::toResponse);
    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest task, String email) {
        Optional<UserResponse> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        //find task by id and user.id
        Task existingTask = taskRepository.find("id = ?1 and user.id = ?2", id, user.get().id()).firstResult();
        if (existingTask == null) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        existingTask.setName(task.name());
        existingTask.setStatus(task.status());
        existingTask.setImageUrl(task.imageUrl());

        taskRepository.persist(existingTask);
        return taskMapper.toResponse(existingTask);
    }

    public boolean deleteTask(Long id, String email) {
        Optional<UserResponse> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        //delete task by id and user.id
        return taskRepository.delete("id = ?1 and user.id = ?2", id, user.get().id()) > 0;
    }






}
