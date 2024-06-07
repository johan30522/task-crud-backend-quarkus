package org.jap.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        Long id,
        @NotBlank
        String userName,
        @NotBlank @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String ageRange
) {
}
