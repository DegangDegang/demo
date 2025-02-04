package com.example.demo.domain.shortessay.domain;

import com.example.demo.domain.shortessay.domain.vo.ShortEssayCommentInfoVo;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShortEssayComment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "short_essay_id")
	private ShortEssay shortEssay;

	@Builder
	public ShortEssayComment(String content, User user, ShortEssay shortEssay) {
		this.content = content;
		this.user = user;
		this.shortEssay = shortEssay;
	}

	public ShortEssayCommentInfoVo getShortEssayCommentInfo() {
		return ShortEssayCommentInfoVo.builder()
			.shortEssayCommentId(id)
			.content(content)
			.userId(user.getId())
			.userNickname(user.getNickname())
			.userProfileImgUrl(user.getProfileImgUrl())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();
	}
}
