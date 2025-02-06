package com.example.demo.domain.paragraph.domain;

import com.example.demo.domain.paragraph.domain.vo.ParagraphCommentInfoVo;
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParagraphComment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "paragraph_id")
	private Paragraph paragraph;

	@Builder
	public ParagraphComment(String content, User user, Paragraph paragraph) {
		this.content = content;
		this.user = user;
		this.paragraph = paragraph;
	}

	public ParagraphCommentInfoVo getParagraphCommentInfo() {
		return ParagraphCommentInfoVo.builder()
			.paragraphCommentId(id)
			.content(content)
			.userId(user.getId())
			.userNickname(user.getNickname())
			.userProfileImgUrl(user.getProfileImgUrl())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();
	}

}
