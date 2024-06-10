package org.jap.core.mapper;

import org.jap.api.dto.UserRegisterRequest;
import org.jap.api.dto.UserResponse;
import org.jap.api.dto.UserTaskCreateRequest;
import org.jap.api.dto.UserUpdateRequest;
import org.jap.infrastructure.entity.User;

import java.util.List;

public interface UserMapper {
    User toEntity(UserResponse userResponse);
    User toEntity(UserRegisterRequest userRegisterRequest);
    User toEntity(UserUpdateRequest userUpdateRequest);
    User toEntity(UserTaskCreateRequest userTaskCreateRequest);
    UserResponse toResponse(User user);
    List<UserResponse> toResponses(List<User> users);
}