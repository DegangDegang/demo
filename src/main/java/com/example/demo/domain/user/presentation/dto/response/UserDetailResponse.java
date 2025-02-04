package com.example.demo.domain.user.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UserDetailResponse {

    private Long userId;
    private String nickName;
    private String role;
    private String profileImgUrl;
    private String biography;
	private Boolean isOwner;
    private Boolean isFollowed;
    private Integer followingCount;
    private Integer followerCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public UserDetailResponse(UserInfoVO userInfoVO) {
        this.userId = userInfoVO.getUserId();
        this.nickName = userInfoVO.getNickname();
        this.role = userInfoVO.getRole();
        this.profileImgUrl = userInfoVO.getProfileImgUrl();
        this.biography = userInfoVO.getBiography();
        this.followingCount = userInfoVO.getFollowingCount();
        this.followerCount = userInfoVO.getFollowerCount();
        this.createdAt = userInfoVO.getCreatedAt();
        this.lastModifiedAt = userInfoVO.getLastModifiedAt();
    }

    public void checkOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public void checkFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

}
