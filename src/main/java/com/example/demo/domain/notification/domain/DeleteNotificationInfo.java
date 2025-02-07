package com.example.demo.domain.notification.domain;

import com.example.demo.domain.user.domain.vo.UserInfoVO;

public interface DeleteNotificationInfo {

	UserInfoVO getReceiver();
	UserInfoVO getSender();
	NotificationType getNotificationType();
	TargetType getTargetType();
	Long getTargetId();
}
