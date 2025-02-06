package com.example.demo.domain.paragraph.domain.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ParagraphInfoVo {

	private final Long paragraphId;
	private final String content;

	private final Long userId;
	private final String userNickname;
	private final String userProfileImgUrl;

	private final Integer likeCount;
	private final Integer commentCount;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

	@Builder
	public ParagraphInfoVo(Long paragraphId, String content, Long userId, String userNickname, String userProfileImgUrl,
		Integer likeCount, Integer commentCount, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.paragraphId = paragraphId;
		this.content = content;
		this.userId = userId;
		this.userNickname = userNickname;
		this.userProfileImgUrl = userProfileImgUrl;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}
}
