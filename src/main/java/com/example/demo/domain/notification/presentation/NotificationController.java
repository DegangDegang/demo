package com.example.demo.domain.notification.presentation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
	public SseEmitter subscribe(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
		User user = userUtils.getUserFromSecurityContext();
		return notificationService.subscribe(user.getId(), lastEventId);
	}

}
