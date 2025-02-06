package com.example.demo.domain.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.user.domain.User;

public interface NotificationService {

	SseEmitter subscribe(Long userId, String lastEventId);

	void send(User receiver, NotificationType notificationType, String content, String url);
}
