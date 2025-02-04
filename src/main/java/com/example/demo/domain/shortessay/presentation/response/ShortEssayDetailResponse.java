package com.example.demo.domain.shortessay.presentation.response;

import java.time.LocalDateTime;

import com.example.demo.domain.shortessay.domain.vo.ShortEssayInfoVo;

import lombok.Getter;

@Getter
public class ShortEssayDetailResponse {

	private final Long shortEssayId;
	private final String content;
	private final String imgUrl;

	private final Integer likeCount;
	private final Integer commentCount;

	private final HostInfo hostInfo;

	private boolean isOwner;
	private boolean isLiked;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

	public ShortEssayDetailResponse(ShortEssayInfoVo shortEssayInfo) {
		this.shortEssayId = shortEssayInfo.getShortEssayId();
		this.content = shortEssayInfo.getContent();
		this.imgUrl = shortEssayInfo.getImgUrl();
		this.likeCount = shortEssayInfo.getLikeCount();
		this.commentCount = shortEssayInfo.getCommentCount();
		this.createdAt = shortEssayInfo.getCreatedAt();
		this.lastModifiedAt = shortEssayInfo.getLastModifiedAt();

		this.hostInfo = new HostInfo(shortEssayInfo.getUserId(), shortEssayInfo.getUserNickname(),
			shortEssayInfo.getUserProfileImgUrl());
	}

	public void checkIsOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public void checkIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
}
