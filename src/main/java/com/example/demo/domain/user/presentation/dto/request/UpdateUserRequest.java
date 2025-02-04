package com.example.demo.domain.user.presentation.dto.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

	private String nickname;
	private String profileImgUrl;
	private String biography;

}
