package org.jap.api.dto;

public record UserResponse(
        Long id,
        String userName,
        String email,
        String phone,
        String ageRange
) {
}
