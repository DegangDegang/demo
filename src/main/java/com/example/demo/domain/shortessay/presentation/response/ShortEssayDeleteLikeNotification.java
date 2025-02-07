package com.example.demo.domain.shortessay.presentation.response;

import com.example.demo.domain.notification.domain.DeleteNotificationInfo;
import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.user.domain.vo.UserInfoVO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShortEssayDeleteLikeNotification implements DeleteNotificationInfo {

	private final UserInfoVO receiver;
	private final UserInfoVO sender;
	private final NotificationType notificationType;
	private final TargetType targetType;
	private final Long targetId;

	@Builder
	public ShortEssayDeleteLikeNotification(UserInfoVO receiver, UserInfoVO sender, Long targetId) {
		this.receiver = receiver;
		this.sender = sender;
		this.notificationType = NotificationType.LIKE;
		this.targetType = TargetType.SHORT_ESSAY;
		this.targetId = targetId;
	}
}
