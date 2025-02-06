package com.example.demo.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

	LIKE("like"), FOLLOW("follow"), COMMENT("comment");

	private final String message;

}
