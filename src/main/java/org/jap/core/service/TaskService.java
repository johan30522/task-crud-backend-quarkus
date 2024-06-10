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
    private ImageService imageService;

    @Inject
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserService userService, UserMapper userMapper, ImageService imageService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService=userService;
        this.userMapper=userMapper;
        this.imageService = imageService;
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

    public boolean createTasks(List<TaskCreateRequest> tasks, User user) {
        tasks.forEach(task -> {
            Task newTask = taskMapper.toEntity(task);
            newTask.setUser(user);
            taskRepository.persist(newTask);
        });
        return true;
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

    public TaskResponse updateStatus(Long id, String status, String email) {
        Optional<UserResponse> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        //find task by id and user.id
        Task existingTask = taskRepository.find("id = ?1 and user.id = ?2", id, user.get().id()).firstResult();
        if (existingTask == null) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        existingTask.setStatus(status);
        taskRepository.persist(existingTask);
        return taskMapper.toResponse(existingTask);
    }

    public boolean deleteTask(Long id, String email) {
        Optional<UserResponse> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        // obtain the task by id and user.id in optional
        Optional<Task> task = taskRepository.find("id = ?1 and user.id = ?2", id, user.get().id()).singleResultOptional();
        if (task.isPresent()){
            //delete image by task id if exist
            imageService.deleteImageByTaskId(id);
            //delete task
            taskRepository.delete(task.get());
            return true;
        } else {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
    }






}
