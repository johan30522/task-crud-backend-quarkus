package org.jap.core.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.jap.api.dto.UserRegisterRequest;
import org.jap.api.dto.UserResponse;
import org.jap.api.dto.UserUpdateRequest;
import org.jap.infrastructure.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserResponse userResponse) {
        if(userResponse==null){
            return null;
        }
        User user= new User();
        user.setId(userResponse.id());
        user.setName(userResponse.userName());
        user.setEmail(userResponse.email());
        user.setPhone(userResponse.phone());
        user.setAgeRange(userResponse.ageRange());

        return user;

    }

    @Override
    public User toEntity(UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest==null){
            return null;
        }
        User user= new User();

        user.setName(userRegisterRequest.userName());
        user.setEmail(userRegisterRequest.email());
        user.setPhone(userRegisterRequest.phone());
        user.setAgeRange(userRegisterRequest.ageRange());

        return user;
    }

    @Override
    public User toEntity(UserUpdateRequest userUpdateRequest) {
        if(userUpdateRequest==null){
            return null;
        }
        User user= new User();

        user.setName(userUpdateRequest.userName());
        user.setEmail(userUpdateRequest.email());
        user.setPhone(userUpdateRequest.phone());
        user.setAgeRange(userUpdateRequest.ageRange());

        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAgeRange()
        );
    }

    @Override
    public List<UserResponse> toResponses(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
