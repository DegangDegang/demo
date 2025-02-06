package com.example.demo.domain.notification.service;

import static io.lettuce.core.RedisURI.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.domain.notification.domain.Notification;
import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.repository.EmitterRepository;
import com.example.demo.domain.notification.domain.repository.NotificationRepository;
import com.example.demo.domain.notification.presentation.dto.response.NotificationResponse;
import com.example.demo.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final EmitterRepository emitterRepository;
	private final NotificationRepository notifyRepository;


	@Override
	public SseEmitter subscribe(Long userId, String lastEventId) {

		String emitterId = makeTimeIncludeId(userId);
		SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
		emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
		emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

		String eventId = makeTimeIncludeId(userId);
		sendNotification(emitter, eventId, emitterId, "EventStream Created.");

		if (hasLostData(lastEventId)) {
			sendLostData(lastEventId, userId, emitterId, emitter);
		}

		return emitter;
	}

	private String makeTimeIncludeId(Long userId) {
		return userId + "_" + System.currentTimeMillis();
	}

	private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.id(eventId)
				.name("sse")
				.data(data)
			);
		} catch (IOException exception) {
			emitterRepository.deleteById(emitterId);
		}
	}

	private boolean hasLostData(String lastEventId) {
		return !lastEventId.isEmpty();
	}

	private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
		Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(userId);
		eventCaches.entrySet().stream()
			.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
			.forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
	}

	@Override
	public void send(User receiver, NotificationType notificationType, String content, String url) {
		Notification notification = notifyRepository.save(createNotification(receiver, notificationType, content, url));

		Long userId = receiver.getId();
		String eventId = userId + "_" + System.currentTimeMillis();
		Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
		emitters.forEach(
			(key, emitter) -> {
				emitterRepository.saveEventCache(key, notification);
				sendNotification(emitter, eventId, key, new NotificationResponse(notification.getNotificationInfo()));
			}
		);
	}

	private Notification createNotification(User receiver, NotificationType notificationType, String content, String url) {
		return Notification.builder()
			.receiver(receiver)
			.notificationType(notificationType)
			.content(content)
			.url(url)
			.build();
	}

}
