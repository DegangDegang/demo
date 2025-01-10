package com.example.demo.global.utils.user;


import com.example.demo.domain.user.domain.User;

public interface UserUtils {

    User getUserById(Long id);

    User getUserFromSecurityContext();

}
