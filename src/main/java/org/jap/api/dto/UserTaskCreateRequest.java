package org.jap.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserTaskCreateRequest(
        @NotBlank
        String userName,
        @NotBlank
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String ageRange,
        @NotBlank
        String password,
        List<TaskCreateRequest> tasks

) {
}
