package com.example.demo.domain.essay.presentation.dto.response;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import lombok.Getter;

@Getter
public class HostInfoDto {

    private final Long userId;
    private final String nickname;
    private final String profilePath;

    public HostInfoDto(UserInfoVO userInfoVO) {
        userId = userInfoVO.getUserId();
        nickname = userInfoVO.getNickname();
        profilePath = userInfoVO.getProfileImgUrl();
    }
}
