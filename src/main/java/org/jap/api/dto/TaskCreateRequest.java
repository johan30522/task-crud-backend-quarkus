package org.jap.api.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateRequest(
        @NotBlank
        String name,
        @NotBlank
        String status,
        @NotBlank
        String imageUrl
) {
}
