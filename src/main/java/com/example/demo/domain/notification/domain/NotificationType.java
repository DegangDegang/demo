package com.example.demo.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

	LIKE("좋아요"), FOLLOW("팔로우"), COMMENT("댓글");

	private final String value;

}
