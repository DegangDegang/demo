package com.example.demo.domain.user.domain.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoVO {
    private final Long userId;

    private final String oauthProvider;
    private final String nickname;
    private final String profileImgUrl;
    private final String role;
    private final String biography;

    private final Integer followingCount;
    private final Integer followerCount;

    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

}