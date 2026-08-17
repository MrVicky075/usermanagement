package com.company.usermanagement.security;

import com.company.usermanagement.entity.UserEntity;
import com.company.usermanagement.service.UserService;
import com.company.usermanagement.session.UserLoginSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {
    private final UserService userService;
    private final UserLoginSession userLoginSession;

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event){
        String email = event.getAuthentication().getName();
        UserEntity user = userService.getEntityByEmail(email);

        userLoginSession.setUserId(user.getUserId());
        userLoginSession.setUserName(user.getUserName());
        userLoginSession.setEmail(user.getEmail());
        userLoginSession.setMobileNo(user.getMobileNo());
        userLoginSession.setRole(user.getRole());
        userLoginSession.setIsActive(user.getIsActive());

    }
}
