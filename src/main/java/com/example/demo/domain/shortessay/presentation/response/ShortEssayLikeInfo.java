package com.example.demo.domain.shortessay.presentation.response;

import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.NotifyInfo;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShortEssayLikeInfo implements NotifyInfo {

	private final HostInfo receiver;
	private final HostInfo sender;
	private final NotificationType notificationType;
	private String content;
	private final TargetType targetType;
	private final Long targetId;
	private String goUrl;

	@Builder
	public ShortEssayLikeInfo(HostInfo receiver,HostInfo sender, NotificationType notificationType, TargetType targetType, Long targetId) {
		this.receiver = receiver;
		this.sender = sender;
		this.notificationType = notificationType;
		this.targetType = targetType;
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
