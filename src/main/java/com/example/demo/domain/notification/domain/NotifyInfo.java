package com.example.demo.domain.notification.domain;

import com.example.demo.domain.user.domain.User;

public interface NotifyInfo {

	User getReceiver();
	String getGoUrl();
	NotificationType getNotificationType();
	String getContent();

}
