package com.example.demo.domain.paragraph.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.paragraph.domain.vo.ParagraphInfoVo;
import com.example.demo.domain.shortessay.presentation.response.HostInfo;

import lombok.Getter;

@Getter
public class ParagraphDetailResponse {

	private final Long paragraphId;
	private final String content;

	private final Integer likeCount;
	private final Integer commentCount;

	private final HostInfo hostInfo;

	private boolean isOwner;
	private boolean isLiked;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

	public ParagraphDetailResponse(ParagraphInfoVo paragraphInfoVo) {
		this.paragraphId = paragraphInfoVo.getParagraphId();
		this.content = paragraphInfoVo.getContent();
		this.likeCount = paragraphInfoVo.getLikeCount();
		this.commentCount = paragraphInfoVo.getCommentCount();
		this.createdAt = paragraphInfoVo.getCreatedAt();
		this.lastModifiedAt = paragraphInfoVo.getLastModifiedAt();

		this.hostInfo = new HostInfo(paragraphInfoVo.getUserId(), paragraphInfoVo.getUserNickname(),
			paragraphInfoVo.getUserProfileImgUrl());
	}

	public void checkIsOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public void checkIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

}
