package org.jap.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jap.api.dto.ImageCreateRequest;
import org.jap.api.dto.ImageResponse;
import org.jap.core.mapper.ImageMapper;
import org.jap.infrastructure.entity.Image;
import org.jap.infrastructure.entity.Task;
import org.jap.infrastructure.repository.ImageRepository;
import org.jap.infrastructure.repository.TaskRepository;

import java.util.Optional;

@ApplicationScoped
public class ImageService {

    private ImageRepository imageRepository;
    private TaskRepository taskRepository;
    private ImageMapper imageMapper;

    @Inject
    public ImageService(ImageRepository imageRepository, TaskRepository taskRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.taskRepository = taskRepository;
        this.imageMapper = imageMapper;
    }

    @Transactional
    public ImageResponse save(ImageCreateRequest request) {
        Optional<Task> taskOptional = taskRepository.findByIdOptional(request.taskId());
        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Task not found with id: " + request.taskId());
        }
        Optional<Image> existingImage = imageRepository.find("task.id", request.taskId()).singleResultOptional();
        Image image;
        if (existingImage.isPresent()) {
            image = existingImage.get();
            image.setName(request.name());
            image.setImageUrl(request.imageUrl());
            image.setImageId(request.imageId());
            imageRepository.persist(image);

        }else{
            Task task = taskOptional.get();
            image = imageMapper.toEntity(request);
            image.setTask(task);
            imageRepository.persist(image);
        }
        return imageMapper.toResponse(image);
    }

    public Optional<ImageResponse> getById(Long id) {
        return imageRepository.findByIdOptional(id)
                .map(imageMapper::toResponse);
    }

    public Optional<ImageResponse> getByTaskId(Long taskId) {
        return imageRepository.find("task.id", taskId)
                .singleResultOptional()
                .map(imageMapper::toResponse);
    }

    public void deleteImageByTaskId(Long taskId) {
        Optional<Image> image = imageRepository.find("task.id", taskId).singleResultOptional();
        image.ifPresent(value -> imageRepository.delete(value));
    }

    @Transactional
    public ImageResponse update(Long id, ImageCreateRequest request) {
        Image existingImage = imageRepository.findById(id);
        if (existingImage == null) {
            throw new IllegalArgumentException("Image not found with id: " + id);
        }
        existingImage.setName(request.name());
        existingImage.setImageUrl(request.imageUrl());
        existingImage.setImageId(request.imageId());
        imageRepository.persist(existingImage);
        return imageMapper.toResponse(existingImage);
    }

    @Transactional
    public boolean delete(Long id) {
        return imageRepository.deleteById(id);
    }
}
