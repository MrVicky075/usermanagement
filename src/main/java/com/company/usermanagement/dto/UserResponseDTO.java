package com.company.usermanagement.dto;

import com.company.usermanagement.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long userId;

    private String userName;

    private String email;

    private String mobileNo;

    private Boolean isActive;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private UserEntity.UserRole role;
}
