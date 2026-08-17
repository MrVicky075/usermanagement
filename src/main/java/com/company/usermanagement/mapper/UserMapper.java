package com.company.usermanagement.mapper;

import com.company.usermanagement.dto.UserRequestDTO;
import com.company.usermanagement.dto.UserResponseDTO;
import com.company.usermanagement.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequestDTO dto){
        return UserEntity.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .mobileNo(dto.getMobileNo())
                .isActive(dto.getIsActive()!=null ? dto.getIsActive() : true)
                .role(dto.getRole())
                .build();
    }

    public UserResponseDTO toResponse(UserEntity entity){
        return UserResponseDTO.builder()
                .userId(entity.getUserId())
                .userName(entity.getUserName())
                .email(entity.getEmail())
                .mobileNo(entity.getMobileNo())
                .isActive(entity.getIsActive())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .role(entity.getRole())
                .build();
    }

    public void updateEntity(UserEntity entity, UserResponseDTO dto){
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setMobileNo(dto.getMobileNo());
        entity.setRole(dto.getRole());
        if(dto.getIsActive() != null){
            entity.setIsActive(dto.getIsActive());
        }
    }
}
