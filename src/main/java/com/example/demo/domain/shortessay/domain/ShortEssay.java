package com.example.demo.domain.shortessay.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.shortessay.domain.vo.ShortEssayInfoVo;
import com.example.demo.domain.shortessay.exception.UnauthorizedShortEssayException;
import com.example.demo.domain.shortessay.presentation.response.ShortEssayDetailResponse;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

	@Embedded
	private Keyword keyword;

	@OneToMany(mappedBy = "shortEssay", cascade = CascadeType.ALL)
	private final List<ShortEssayLike> likes = new ArrayList<>();

	@OneToMany(mappedBy = "shortEssay", cascade = CascadeType.ALL)
	private final List<ShortEssayComment> comments = new ArrayList<>();

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
			.keyword1(keyword.getKeyword1())
			.keyword2(keyword.getKeyword2())
			.keyword3(keyword.getKeyword3())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();
	}

	@Builder
	public ShortEssay(String content, String imgUrl, Keyword keyword, User user) {
		this.content = content;
		this.imgUrl = imgUrl;
		this.keyword = keyword;
		this.user = user;
	}

	public void validUserIsHost(Long id) {
		if (!user.getId().equals(id)) {
			throw new RuntimeException();
		}
	}
}
