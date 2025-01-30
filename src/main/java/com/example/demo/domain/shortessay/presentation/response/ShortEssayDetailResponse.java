package com.example.demo.domain.shortessay.presentation.response;


import com.example.demo.domain.shortessay.domain.vo.ShortEssayInfoVo;
import lombok.Getter;

@Getter
public class ShortEssayDetailResponse {

    private final Long shortEssayId;
    private final String content;
    private final String imgUrl;

    private final Long userId;
    private final String userNickname;

    private Long likeCount;
    private Long commentCount;

    public ShortEssayDetailResponse(ShortEssayInfoVo shortEssayInfo) {
        this.shortEssayId = shortEssayInfo.getShortEssayId();
        this.content = shortEssayInfo.getContent();
        this.imgUrl = shortEssayInfo.getImgUrl();
        this.userId = shortEssayInfo.getUserId();
        this.userNickname = shortEssayInfo.getUserNickname();
    }
}
