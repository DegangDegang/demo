package com.example.demo.domain.user.presentation.dto.response;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String profilePath;


    public UserProfileResponse(UserInfoVO userInfo) {
        this.id = userInfo.getUserId();
        this.name = userInfo.getNickname();
        this.email = userInfo.getEmail();
        this.profilePath = userInfo.getProfilePath();
    }
}
