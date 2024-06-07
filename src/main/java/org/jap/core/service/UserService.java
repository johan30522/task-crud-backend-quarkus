package org.jap.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jap.api.dto.UserRegisterRequest;
import org.jap.api.dto.UserResponse;
import org.jap.api.dto.UserUpdateRequest;
import org.jap.core.mapper.UserMapper;
import org.jap.infrastructure.entity.User;
import org.jap.infrastructure.repository.UserRepository;
import org.jap.core.util.RSAUtil;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    private KeyPair keyPair;

    @Inject
    public UserService(UserRepository userRepository, UserMapper userMapper) throws Exception {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.keyPair = RSAUtil.generateKeyPair();
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.listAll();
        return userMapper.toResponses(users);
    }

    public UserResponse createUser(UserRegisterRequest userReq) throws Exception {

        System.out.println("Password de request: " + userReq.password());
        String encryptedPassword = RSAUtil.encrypt(userReq.password(), keyPair.getPublic());
        System.out.println("Password Encriptado: " + encryptedPassword);
        User user = userMapper.toEntity(userReq);
        user.setPassword(encryptedPassword);
        userRepository.persist(user);
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(Long id){

       User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        return userMapper.toResponse(existingUser);
    }

    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse);
    }

    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        existingUser.setName(userUpdateRequest.userName());
        existingUser.setEmail(userUpdateRequest.email());
        existingUser.setPhone(userUpdateRequest.phone());
        existingUser.setAgeRange(userUpdateRequest.ageRange());
        userRepository.persist(existingUser);
        return userMapper.toResponse(existingUser);
    }

    public UserResponse validateUser(String userName, String password) {
        Optional<User> userOpt = userRepository.findByEmail(userName);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        try {
            String decryptedPassword = RSAUtil.decrypt(user.getPassword(), keyPair.getPrivate());
            System.out.println("Decrypted Password: " + decryptedPassword);
            if (!password.equals(decryptedPassword)){
                return null;
            }
            return userMapper.toResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error decrypting password", e);

        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }



}