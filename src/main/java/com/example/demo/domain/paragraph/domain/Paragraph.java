package com.example.demo.domain.paragraph.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.novel.domain.Novel;
import com.example.demo.domain.paragraph.domain.vo.ParagraphInfoVo;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "paragraphs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paragraph extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paragraph_id")
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "novel_id")
	private Novel novel;

	private Boolean isFinalized;

	private Boolean isClosed;

	private Boolean isAdopted;

	@OneToMany(mappedBy = "paragraph", fetch = FetchType.LAZY)
	private final List<ParagraphComment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "paragraph", fetch = FetchType.LAZY)
	private final List<ParagraphLike> likes = new ArrayList<>();

	@Builder
	public Paragraph(String content, User user, Novel novel) {
		this.content = content;
		this.user = user;
		this.novel = novel;
		this.isClosed  = false;
		this.isFinalized = false;
		this.isAdopted = false;
	}

	public ParagraphInfoVo getParagraphInfo() {
		return ParagraphInfoVo.builder()
			.paragraphId(id)
			.content(content)
			.userId(user.getId())
			.userNickname(user.getNickname())
			.userProfileImgUrl(user.getProfileImgUrl())
			.likeCount(likes.size())
			.commentCount(comments.size())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void endWrite() {
		this.isFinalized  = true;
	}

	public void endTime() {
		this.isClosed  = true;
	}

	public void adopt() {
		this.isAdopted = true;
	}
}