package com.company.usermanagement.service;

import com.company.usermanagement.dto.ChangePasswordDTO;
import com.company.usermanagement.dto.UserRequestDTO;
import com.company.usermanagement.dto.UserResponseDTO;
import com.company.usermanagement.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);
    UserResponseDTO updateUser(Long userId, UserResponseDTO request);
    void deleteUser(Long userId);
    UserResponseDTO getUserById(Long userId);
    List<UserResponseDTO> getAllUsers();

    void changeActiveStatus(Long userId);
    void changePassword(Long userId, ChangePasswordDTO request);
    UserEntity getEntityByEmail(String email);
}
