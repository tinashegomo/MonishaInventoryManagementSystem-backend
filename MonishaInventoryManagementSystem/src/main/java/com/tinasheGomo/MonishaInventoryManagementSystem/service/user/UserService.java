package com.tinasheGomo.MonishaInventoryManagementSystem.service.user;

import com.tinasheGomo.MonishaInventoryManagementSystem.dto.user.UserResponseDTO;
import com.tinasheGomo.MonishaInventoryManagementSystem.entity.user.UserEntity;
import com.tinasheGomo.MonishaInventoryManagementSystem.enums.UserRole;
import com.tinasheGomo.MonishaInventoryManagementSystem.exception.exceptions.NotFoundException;
import com.tinasheGomo.MonishaInventoryManagementSystem.mapper.user.UserMapper;
import com.tinasheGomo.MonishaInventoryManagementSystem.repository.user.UserRepository;
import com.tinasheGomo.MonishaInventoryManagementSystem.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Get current logged in user
    public UserResponseDTO getCurrentLoggedInUser() {

        UserEntity user = SecurityUtils.getCurrentUser().getUser();

        return userMapper.toResponse(user);
    }

    // Get current logged in user role
    public UserRole getCurrentLoggedInUserRole() {

        return SecurityUtils.getCurrentUserRole();
    }

    // Get a single user by id
    public UserResponseDTO getUserById(UUID id) {

        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    // Get all users
    public List<UserResponseDTO> getAllUsers() {

        List<UserEntity> users = userRepository.findAll();

        return userMapper.toResponseList(users);
    }

    // Delete user
    public void deleteUserById(UUID id) {

        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }

    // Update user role
    public UserResponseDTO updateUserRole(UUID userId, UserRole userRole) {
        UserEntity user = userRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException("User not found"));

        user.setUserRole(userRole);
        UserEntity updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }
}
