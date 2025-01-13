package com.example.demo.global.utils.user;

import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test")
class UserUtilsImplTest {

    @Autowired
    public UserUtils userUtils;

    @Autowired
    public UserRepository userRepository;

    @BeforeEach
    @DisplayName("유저를 db에 넣기")
    public void makeUser() {
        User test12 = User.builder().nickname("test12").build();
        userRepository.save(test12);

    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("유저를 가져오는 utils test")
    public void getUserTest() {
        User user = userUtils.getUserById(1L);
        Assertions.assertThat(user.getNickname()).isEqualTo("test12");
    }
}