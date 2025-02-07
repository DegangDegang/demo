package com.example.demo.domain.notification.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.notification.presentation.dto.response.NotificationDetailResponse;
import com.example.demo.domain.notification.presentation.dto.response.NotificationSizeResponse;
import com.example.demo.domain.shortessay.presentation.response.HostInfo;
import com.example.demo.domain.user.domain.User;

public interface NotificationService {

	SseEmitter subscribe(Long userId, String lastEventId);

	void send(HostInfo receiver, HostInfo sender, NotificationType notificationType, TargetType targetType, Long targetId, String content, String url);

	Slice<NotificationDetailResponse> getUsersNotifications(User user, Pageable pageable);

	NotificationSizeResponse getNotificationSize(User user);

	void readNotification(Long notificationId, User user);

	void deleteNotification(Long senderId, Long receiverId, TargetType targetType, Long targetId);
}
