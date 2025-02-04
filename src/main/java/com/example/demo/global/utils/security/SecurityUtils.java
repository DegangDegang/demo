package com.example.demo.global.utils.security;

import com.example.demo.global.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw UserNotFoundException.EXCEPTION;
        }
        return Long.valueOf(authentication.getName());
    }

}
