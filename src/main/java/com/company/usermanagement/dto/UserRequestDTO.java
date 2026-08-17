package com.company.usermanagement.dto;

import com.company.usermanagement.entity.UserEntity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    private Long userId;

    @NotBlank(message = "User name is required")
    @Size(max = 100, message = "User name cannot exceed 100 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    private String password;

    @NotBlank(message = "Mobile number is required")
    private String mobileNo;

    private Boolean isActive;

    @NotNull(message = "Role is required")
    private UserRole role;
}
