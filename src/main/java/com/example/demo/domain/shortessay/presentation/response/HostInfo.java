package com.example.demo.domain.shortessay.presentation.response;

import lombok.Getter;

@Getter
public class HostInfo {

	private final Long userId;
	private final String nickname;
	private final String profileImgUrl;

	public HostInfo(Long userId, String nickname, String profileImgUrl) {
		this.userId = userId;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
	}
}
