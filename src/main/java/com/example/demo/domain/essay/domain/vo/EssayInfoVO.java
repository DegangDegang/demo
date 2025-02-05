package com.example.demo.domain.essay.domain.vo;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EssayInfoVO {

    private final Long essayId;

    private final String title;

    private final String content;

    private final String imageUrl;

    private final LocalDateTime createAt;

    private final LocalDateTime lastModifyAt;

    private final UserInfoVO hostInfoVO;

    private final Boolean isDraft;

    private final String sentence;
}
