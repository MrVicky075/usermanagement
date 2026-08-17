package com.company.usermanagement.session;

import com.company.usermanagement.entity.UserEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Data
@Component("userLoginSession")
//@Scope(value = WebApplicationContext.SCOPE_SESSION)
@Scope(
        value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class UserLoginSession {
    private Long userId;
    private String userName;
    private String email;
    private String mobileNo;
    private UserEntity.UserRole role;
    private Boolean isActive;

    public void clear() {
        this.userId = null;
        this.userName = null;
        this.email = null;
        this.mobileNo = null;
        this.role = null;
        this.isActive = null;
    }
}
