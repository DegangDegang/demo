package com.example.demo.domain.shortessay.domain;

import com.example.demo.domain.shortessay.domain.vo.ShortEssayInfoVo;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortEssay {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ShortEssay(String content, String imgUrl, User user) {
        this.content = content;
        this.imgUrl = imgUrl;
        this.user = user;
    }

    public ShortEssayInfoVo getShortEssayInfo() {
        return ShortEssayInfoVo.builder()
            .shortEssayId(id)
            .content(content)
            .imgUrl(imgUrl)
            .userId(user.getId())
            .userNickname(user.getNickname())
            .build();
    }
}
