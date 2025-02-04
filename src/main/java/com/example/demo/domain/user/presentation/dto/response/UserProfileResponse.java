package com.example.demo.domain.user.presentation.dto.response;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private final Long id;
    private final String nickname;
    private final String profileImgUrl;
    private final String biography;


    public UserProfileResponse(UserInfoVO userInfo) {
        this.id = userInfo.getUserId();
        this.nickname = userInfo.getNickname();
        this.profileImgUrl = userInfo.getProfileImgUrl();
        this.biography = userInfo.getBiography();
    }
}
