package com.company.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Tbl_UserLogin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "userName", nullable = false, length = 100)
    private String userName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "mobileNo", length = 20)
    private String mobileNo;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column(name="createdOn", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updatedOn")
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UserRole role;

    public enum UserRole{
        ADMIN,
        SR_DEVELOPER,
        DEVELOPER,
        SUPPORT
    }

}
