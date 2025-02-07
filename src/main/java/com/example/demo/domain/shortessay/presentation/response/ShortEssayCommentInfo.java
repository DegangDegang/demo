package com.example.demo.domain.shortessay.presentation.response;

import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.NotifyInfo;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShortEssayCommentInfo implements NotifyInfo {

	private final HostInfo receiver;
	private final HostInfo sender;
	private String goUrl;
	private final NotificationType notificationType;
	private final TargetType targetType;
	private final Long targetId;
	private String content;

	@Builder
	public ShortEssayCommentInfo(HostInfo receiver, HostInfo sender, Long targetId) {
		this.receiver = receiver;
		this.sender = sender;
		this.notificationType = NotificationType.COMMENT;
		this.targetType = TargetType.SHORT_ESSAY;
		this.targetId = targetId;
	}

	@Override
	public void writeContent(String content) {
		this.content = content;
	}

	@Override
	public void writeGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}
}
