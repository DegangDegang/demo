package com.example.demo.domain.shortessay.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.shortessay.domain.vo.ShortEssayInfoVo;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortEssay extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "short_essay_id")
	private Long id;

	private String content;

	private String imgUrl;

	@OneToMany(mappedBy = "shortEssay", cascade = CascadeType.ALL)
	private final List<ShortEssayLike> likes = new ArrayList<>();

	@OneToMany(mappedBy = "shortEssay", cascade = CascadeType.ALL)
	private final List<ShortEssayComment> comments = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "short_essay_keyword", joinColumns = @JoinColumn(name = "short_essay_id"))
	@Column(name = "keyword")
	private final List<String> keywords = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public ShortEssayInfoVo getShortEssayInfo() {
		return ShortEssayInfoVo.builder()
			.shortEssayId(id)
			.content(content)
			.imgUrl(imgUrl)
			.userId(user.getId())
			.userNickname(user.getNickname())
			.userProfileImgUrl(user.getProfileImgUrl())
			.likeCount(likes.size())
			.commentCount(comments.size())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.keywords(new ArrayList<>(keywords)
			)
			.build();
	}

	@Builder
	public ShortEssay(String content, String imgUrl, User user, List<String> keywords) {
		this.content = content;
		this.imgUrl = imgUrl;
		this.user = user;
		this.keywords.addAll(keywords);
	}

	public void validUserIsHost(Long id) {
		if (!user.getId().equals(id)) {
			throw new RuntimeException();
		}
	}
}
