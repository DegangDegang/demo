package com.example.demo.domain.shortessay.domain.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShortEssayInfoVo {

    private final Long shortEssayId;
    private final String content;
    private final String imgUrl;

    private final Long userId;
    private final String userNickname;
    private final String userProfileImgUrl;

    private final Integer likeCount;
    private final Integer commentCount;

    private final String keyword1;
    private final String keyword2;
    private final String keyword3;

    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

}
