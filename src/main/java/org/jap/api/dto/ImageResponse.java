package org.jap.api.dto;

public record ImageResponse(
        Long id,
        String name,
        String imageUrl,
        String imageId,
        Long taskId
) {
}
