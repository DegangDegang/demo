package com.example.demo.domain.paragraph.domain.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ParagraphCommentInfoVo {

	private final Long paragraphCommentId;
	private final String content;

	private final Long userId;
	private final String userNickname;
	private final String userProfileImgUrl;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

	@Builder
	public ParagraphCommentInfoVo(Long paragraphCommentId, String content, Long userId, String userNickname,
		String userProfileImgUrl, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
		this.paragraphCommentId = paragraphCommentId;
		this.content = content;
		this.userId = userId;
		this.userNickname = userNickname;
		this.userProfileImgUrl = userProfileImgUrl;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
	}
}
