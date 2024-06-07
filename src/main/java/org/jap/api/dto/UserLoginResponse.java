package org.jap.api.dto;

public record UserLoginResponse(
        UserResponse user,
        String token,
        String csrfToken
) {
}
