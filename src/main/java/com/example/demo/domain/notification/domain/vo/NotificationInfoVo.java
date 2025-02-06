package com.example.demo.domain.notification.domain.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationInfoVo {

	private final Long notificationId;
	private final String content;
	private final String url;
	private final Boolean isRead;

	private final Long userId;
	private final String userNickname;
	private final String userProfileImgUrl;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

}
