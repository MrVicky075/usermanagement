package com.company.usermanagement.config;

import com.company.usermanagement.entity.UserEntity;
import com.company.usermanagement.entity.UserEntity.UserRole;
import com.company.usermanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@localmail.com")) {

            UserEntity admin =UserEntity.builder()
                            .userName("System Administrator")
                            .email("admin@localmail.com")
                            .password(passwordEncoder.encode("123"))
                            .mobileNo("9999999999")
                            .isActive(true)
                            .role(UserRole.ADMIN)
                            .createdOn(LocalDateTime.now())
                            .updatedOn(LocalDateTime.now())
                            .build();
            userRepository.save(admin);
        }
    }
}