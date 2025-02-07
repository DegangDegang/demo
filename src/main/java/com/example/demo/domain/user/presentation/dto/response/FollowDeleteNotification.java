package com.example.demo.domain.user.presentation.dto.response;

import com.example.demo.domain.notification.domain.DeleteNotificationInfo;
import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.user.domain.vo.UserInfoVO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowDeleteNotification implements DeleteNotificationInfo {

	private final UserInfoVO receiver;
	private final UserInfoVO sender;
	private final NotificationType notificationType;
	private final TargetType targetType;
	private final Long targetId;
	private final String content;

	@Builder
	public FollowDeleteNotification(UserInfoVO receiver, UserInfoVO sender, NotificationType notificationType,
		TargetType targetType,
		Long targetId, String content) {
		this.receiver = receiver;
		this.sender = sender;
		this.notificationType = notificationType;
		this.targetType = targetType;
		this.targetId = targetId;
		this.content = content;
	}
}
