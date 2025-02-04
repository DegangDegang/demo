package com.example.demo.domain.shortessay.presentation.response;

import java.time.LocalDateTime;

import com.example.demo.domain.shortessay.domain.vo.ShortEssayCommentInfoVo;

import lombok.Getter;

@Getter
public class ShortEssayCommentResponse {

	private final Long commentId;
	private final String content;
	private final HostInfo hostInfo;
	private boolean isOwner;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

	public ShortEssayCommentResponse (ShortEssayCommentInfoVo shortEssayCommentInfoVo) {
		this.commentId = shortEssayCommentInfoVo.getShortEssayCommentId();
		this.content = shortEssayCommentInfoVo.getContent();
		this.createdAt = shortEssayCommentInfoVo.getCreatedAt();
		this.lastModifiedAt = shortEssayCommentInfoVo.getLastModifiedAt();

		this.hostInfo = new HostInfo(shortEssayCommentInfoVo.getUserId(), shortEssayCommentInfoVo.getUserNickname(),
			shortEssayCommentInfoVo.getUserProfileImgUrl());
	}

	public void checkOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

}
