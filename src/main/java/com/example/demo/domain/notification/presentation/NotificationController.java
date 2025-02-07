package com.example.demo.domain.notification.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.domain.notification.presentation.dto.response.NotificationDetailResponse;
import com.example.demo.domain.notification.presentation.dto.response.NotificationSizeResponse;
import com.example.demo.domain.notification.service.NotificationService;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.user.UserUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;
	private final UserUtils userUtils;

	@GetMapping(value = "/subscribe", produces = "text/event-stream")
	public SseEmitter subscribe(
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
		User user = userUtils.getUserFromSecurityContext();
		return notificationService.subscribe(user.getId(), lastEventId);
	}

	@GetMapping
	public Slice<NotificationDetailResponse> getNotifications(
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		User user = userUtils.getUserFromSecurityContext();
		return notificationService.getUsersNotifications(user, pageable);
	}

	@GetMapping("/size")
	public NotificationSizeResponse getSize() {
		User user = userUtils.getUserFromSecurityContext();
		return notificationService.getNotificationSize(user);
	}

	@GetMapping("/{notificationId}")
	public void readNotification(@PathVariable Long notificationId) {
		User user = userUtils.getUserFromSecurityContext();
		notificationService.readNotification(notificationId, user);
	}


}
