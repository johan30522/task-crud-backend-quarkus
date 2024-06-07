package org.jap.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ImageCreateRequest(
        @NotBlank
        String name,
        @NotBlank
        String imageUrl,
        @NotBlank
        String imageId,
        Long taskId
) {
}
