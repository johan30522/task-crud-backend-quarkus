package org.jap.api.dto;

public record TaskResponse(
        Long id,
        String name,
        String status,
        String imageUrl,
        ImageResponse image
) {
}
