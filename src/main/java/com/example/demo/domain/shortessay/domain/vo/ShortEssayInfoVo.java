package com.example.demo.domain.shortessay.domain.vo;

import java.time.LocalDateTime;
import java.util.List;

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

    private final List<String> keywords;

    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;
}
