package com.example.demo.domain.notification.presentation.dto.response;

import lombok.Getter;

@Getter
public class NotificationSizeResponse {

	private final Long unreadCount;

	public NotificationSizeResponse(Long unreadCount) {
		this.unreadCount = unreadCount;
	}
}
