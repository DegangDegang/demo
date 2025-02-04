package com.example.demo.global.utils.user;

import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import com.example.demo.global.exception.UserNotFoundException;
import com.example.demo.global.security.JwtTokenProvider;
import com.example.demo.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserUtilsImpl implements UserUtils{

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public User getUserFromSecurityContext() {
        Long currentUserId = securityUtils.getCurrentUserId();
        return getUserById(currentUserId);
    }

    @Override
    public User getUserFromToken(String token) {
        Long userId = jwtTokenProvider.parseAccessToken(token);
        return getUserById(userId);
    }


}
