package org.jap.api.dto;

public record UserCreateRequest(
        String userName,
        String email,
        String phone,
        String ageRange,
        String password
) {
}
