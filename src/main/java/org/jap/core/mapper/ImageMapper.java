package org.jap.core.mapper;

import org.jap.api.dto.ImageCreateRequest;
import org.jap.api.dto.ImageResponse;
import org.jap.infrastructure.entity.Image;

public interface ImageMapper {
    public Image toEntity(ImageCreateRequest request);
    public ImageResponse toResponse(Image image);
}
