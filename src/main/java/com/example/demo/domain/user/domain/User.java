package com.example.demo.domain.user.domain;

import com.example.demo.domain.user.domain.vo.UserInfoVO;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.lang.Nullable;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String nickname;

	private String oauthProvider;

	private String oauthId;

	private String profileImgUrl;

	private String biography;

	@Enumerated(EnumType.STRING)
	private final AccountRole accountRole = AccountRole.USER;

	@OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
	private final List<Follow> followerList = new ArrayList<>();

	@OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
	private final List<Follow> followingList = new ArrayList<>();

	@Builder
	public User(
		Long id,
		String nickname,
		String oauthProvider,
		String oauthId,
		String profileImgUrl) {
		this.id = id;
		this.nickname = nickname;
		this.oauthProvider = oauthProvider;
		this.oauthId = oauthId;
		this.profileImgUrl = profileImgUrl;
	}

	public UserInfoVO getUserInfo() {
		return UserInfoVO.builder()
			.userId(id)
			.oauthProvider(oauthProvider)
			.nickname(nickname)
			.profileImgUrl(profileImgUrl)
			.role(accountRole.getValue())
			.biography(biography)
			.followingCount(followingList.size())
			.followerCount(followerList.size())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();
	}

	public void update(String nickname, String profileImgUrl, String biography) {
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
		this.biography = biography;

	}
}
