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
	private final String type;

	private final Long receiverId;
	private final String receiverNickname;
	private final String receiverProfileImgUrl;

	private final Long senderId;
	private final String senderNickname;
	private final String senderProfileImgUrl;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedAt;

}
