package com.example.demo.domain.essay.domain.vo;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EssayCommentInfoVo {

    private final Long essayCommentId;
    private final String content;
    private final UserInfoVO hostInfoVO;
}
