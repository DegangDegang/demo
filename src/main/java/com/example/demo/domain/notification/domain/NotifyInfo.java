package com.example.demo.domain.notification.domain;

import com.example.demo.domain.shortessay.presentation.response.HostInfo;

public interface NotifyInfo {

	HostInfo getReceiver();
	HostInfo getSender();
	String getGoUrl();
	NotificationType getNotificationType();
	TargetType getTargetType();
	Long getTargetId();
	String getContent();

	void writeContent(String content);
	void writeGoUrl(String goUrl);

}
