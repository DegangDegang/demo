package com.example.demo.domain.notification.presentation.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.notification.domain.vo.NotificationInfoVo;
import com.example.demo.domain.shortessay.presentation.response.HostInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationResponse {

	private final Long notificationId;

	private final String content;

	private final String url;

	private final Boolean isRead;

	private final HostInfo hostInfo;

	private final LocalDateTime createdAt;

	private final LocalDateTime lastModifiedAt;

	@Builder
	public NotificationResponse(NotificationInfoVo notificationInfoVo) {
		this.notificationId = notificationInfoVo.getNotificationId();
		this.content = notificationInfoVo.getContent();
		this.url = notificationInfoVo.getUrl();
		this.isRead = notificationInfoVo.getIsRead();
		this.createdAt = notificationInfoVo.getCreatedAt();
		this.lastModifiedAt = notificationInfoVo.getLastModifiedAt();

		this.hostInfo = new HostInfo(notificationInfoVo.getUserId(), notificationInfoVo.getUserNickname(), notificationInfoVo.getUserProfileImgUrl());
	}
}
