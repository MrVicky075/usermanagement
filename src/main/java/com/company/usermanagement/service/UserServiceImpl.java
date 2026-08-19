package com.company.usermanagement.service;

import com.company.usermanagement.dto.ChangePasswordDTO;
import com.company.usermanagement.dto.UserRequestDTO;
import com.company.usermanagement.dto.UserResponseDTO;
import com.company.usermanagement.entity.UserEntity;
import com.company.usermanagement.exception.BusinessException;
import com.company.usermanagement.exception.ResourceNotFoundException;
import com.company.usermanagement.mapper.UserMapper;
import com.company.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("Email already registered");
        }
        UserEntity entity = mapper.toEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setIsActive(true);
        entity.setCreatedOn(LocalDateTime.now());
        entity.setUpdatedOn(LocalDateTime.now());

        return mapper.toResponse(userRepository.save(entity));
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserResponseDTO request) {
        UserEntity entity = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found: " + userId));
        if(!entity.getEmail().equalsIgnoreCase(request.getEmail())){
            if(userRepository.existsByEmail(request.getEmail())){
                throw new BusinessException("Email already registered");
            }
        } 
        mapper.updateEntity(entity,request);
        entity.setUpdatedOn(LocalDateTime.now());
        return mapper.toResponse(userRepository.save(entity));
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity entity = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
        return mapper.toResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void changeActiveStatus(Long userId) {
        UserEntity entity = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
        entity.setIsActive(!Boolean.TRUE.equals(entity.getIsActive()));
        entity.setUpdatedOn(LocalDateTime.now());
        userRepository.save(entity);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO request) {
        UserEntity entity = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"+userId));
        if(request.getMode().equalsIgnoreCase("user")){
            if(!passwordEncoder.matches(request.getOldPassword(),entity.getPassword())){
                throw new BusinessException("Old password is incorrect");
            }
            if(!passwordEncoder.matches(request.getOldPassword(),request.getConfirmPassword())){
                throw new BusinessException("New password and confirm password do not match");
            }
        }
        entity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        entity.setUpdatedOn(LocalDateTime.now());
        userRepository.save(entity);
    }

    @Override
    public UserEntity getEntityByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User Not found"));
    }
}
