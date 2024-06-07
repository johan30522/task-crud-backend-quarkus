package org.jap.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @NotBlank(message = "Username cannot be blank")
        String userName,
        @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Phone cannot be blank")
        String phone,
        @NotBlank(message = "Age range cannot be blank")
        String ageRange,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
