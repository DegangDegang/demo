package com.example.demo.domain.shortessay.domain.vo;

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
}
