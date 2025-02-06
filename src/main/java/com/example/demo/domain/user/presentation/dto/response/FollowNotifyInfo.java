package com.example.demo.domain.user.presentation.dto.response;

import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.NotifyInfo;
import com.example.demo.domain.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowNotifyInfo implements NotifyInfo {

	private final User receiver;
	private final String goUrl;
	private final NotificationType notificationType;
	private final String content;

	@Builder
	public FollowNotifyInfo(User receiver, String goUrl, NotificationType notificationType, String content) {
		this.receiver = receiver;
		this.goUrl = goUrl;
		this.notificationType = notificationType;
		this.content = content;
	}

}
