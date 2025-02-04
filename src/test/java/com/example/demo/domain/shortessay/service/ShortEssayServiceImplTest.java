package com.example.demo.domain.shortessay.service;

import com.example.demo.domain.shortessay.domain.ShortEssay;
import com.example.demo.domain.shortessay.domain.repository.ShortEssayRepository;
import com.example.demo.domain.shortessay.exception.ShortEssayNotFoundException;
import com.example.demo.domain.shortessay.exception.UnauthorizedShortEssayException;
import com.example.demo.domain.shortessay.presentation.request.WriteShortEssayRequest;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ShortEssayServiceImplTest {

    @Autowired
    private ShortEssayService shortEssayService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShortEssayRepository shortEssayRepository;

    User testUser, testUser2;

    ShortEssay testEssay;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .nickname("닉네임1")
                .build();

        testUser2 = User.builder()
                .nickname("닉네임2")
                .build();
        userRepository.save(testUser);
        userRepository.save(testUser2);

        testEssay = ShortEssay.builder()
                .content("내용")
                .imgUrl("imageUrl")
                .user(testUser)
                .build();
        shortEssayRepository.save(testEssay);
    }

    @DisplayName("단필 작성")
    @Test
    void writeShortEssay_success() {
        // given
        WriteShortEssayRequest writeShortEssayRequest = new WriteShortEssayRequest("단필입니다", "http:localhost:8080");

        // when
        ShortEssayDetailResponse shortEssayDetailResponse = shortEssayService.write(writeShortEssayRequest, testUser);

        // then
        assertThat(shortEssayDetailResponse.getContent()).isEqualTo(writeShortEssayRequest.getContent());
        assertThat(shortEssayDetailResponse.getHostInfo().getUserId()).isEqualTo(testUser.getId());
    }

    @DisplayName("단필 조회 성공")
    @Test
    void readShortEssay_success() {
        // given

        // when
        ShortEssayDetailResponse shortEssayDetailResponse = shortEssayService.read(testEssay.getId(), testUser);
        // then
        assertThat(shortEssayDetailResponse.getContent()).isEqualTo(testEssay.getContent());
    }

    @DisplayName("단필 조회 실패")
    @Test
    void readShortEssay_fail() {
        // given
        // when & then
        Assertions.assertThrows(ShortEssayNotFoundException.class, () -> shortEssayService.read(999L, testUser));
    }

    @DisplayName("단필 삭제 성공")
    @Test
    void deleteShortEssay_success() {
        // given
        // when
        shortEssayService.delete(testEssay.getId(), testUser);

        // then
        Assertions.assertThrows(ShortEssayNotFoundException.class, () -> shortEssayService.read(testEssay.getId(), testUser));
    }

    @DisplayName("단필 삭제 실패")
    @Test
    void deleteShortEssay_fail() {
        Assertions.assertThrows(UnauthorizedShortEssayException.class, () -> shortEssayService.delete(testEssay.getId(), testUser2));
    }

    @DisplayName("단필 목록 조회")
    @Test
    void readShortEssayList() {

        // given
        for (int i = 0; i < 100; i++) {
            ShortEssay shortEssay = ShortEssay.builder()
                    .content("내용" + i)
                    .imgUrl("imageUrl" + i)
                    .user(testUser)
                    .build();
            shortEssayRepository.save(shortEssay);
        }


        // when
        List<ShortEssayDetailResponse> next = shortEssayService.getShortEssays(testEssay.getId(), 10, "next");

        System.out.println("--------- insert End ------------");
        System.out.println("test.getId() = " + testEssay.getId());
        for (ShortEssayDetailResponse shortEssayDetailResponse : next) {
            System.out.println(shortEssayDetailResponse.getShortEssayId());
        }



    }
}