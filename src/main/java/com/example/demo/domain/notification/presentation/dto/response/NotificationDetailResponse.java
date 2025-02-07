package com.example.demo.domain.notification.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.notification.domain.vo.NotificationInfoVo;
import com.example.demo.domain.shortessay.presentation.response.HostInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationDetailResponse {

	private final Long notificationId;

	private final String content;

	private final String url;

	private final Boolean isRead;

	private final HostInfo senderInfo;

	private final LocalDateTime createdAt;

	private final LocalDateTime lastModifiedAt;

	private final String notificationType;

	@Builder
	public NotificationDetailResponse(NotificationInfoVo notificationInfoVo) {
		this.notificationId = notificationInfoVo.getNotificationId();
		this.content = notificationInfoVo.getContent();
		this.url = notificationInfoVo.getUrl();
		this.isRead = notificationInfoVo.getIsRead();
		this.createdAt = notificationInfoVo.getCreatedAt();
		this.lastModifiedAt = notificationInfoVo.getLastModifiedAt();
		this.notificationType = notificationInfoVo.getType();

		this.senderInfo = new HostInfo(notificationInfoVo.getSenderId(), notificationInfoVo.getSenderNickname(),
			notificationInfoVo.getSenderProfileImgUrl());
	}
}
