package org.jap.core.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.jap.api.dto.*;
import org.jap.infrastructure.entity.Image;

@ApplicationScoped
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image toEntity(ImageCreateRequest request) {
        if (request == null) {
            return null;
        }
        Image image = new Image();
        image.setName(request.name());
        image.setImageUrl(request.imageUrl());
        image.setImageId(request.imageId());
        return image;
    }

    @Override
    public ImageResponse toResponse(Image image) {
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
