package com.example.demo.domain.notification.service;

import static io.lettuce.core.RedisURI.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.domain.notification.domain.Notification;
import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.notification.domain.repository.EmitterRepository;
import com.example.demo.domain.notification.domain.repository.NotificationRepository;
import com.example.demo.domain.notification.presentation.dto.response.NotificationDetailResponse;
import com.example.demo.domain.notification.presentation.dto.response.NotificationSizeResponse;
import com.example.demo.domain.shortessay.presentation.response.HostInfo;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import com.example.demo.global.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final EmitterRepository emitterRepository;
	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	@Override
	public SseEmitter subscribe(Long userId, String lastEventId) {

		String emitterId = makeTimeIncludeId(userId);
		SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT * 1000 * 1000));
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
	public void send(HostInfo receiver, HostInfo sender, NotificationType notificationType, TargetType targetType,
		Long targetId, String content, String url) {

		User receiverUser = userRepository.findById(receiver.getUserId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);
		User sendUser = userRepository.findById(sender.getUserId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);

		Notification notification = notificationRepository.save(
			createNotification(receiverUser, sendUser, notificationType, targetType, targetId, content, url));

		Long userId = receiver.getUserId();
		String eventId = userId + "_" + System.currentTimeMillis();
		Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
		emitters.forEach(
			(key, emitter) -> {
				emitterRepository.saveEventCache(key, notification);
				sendNotification(emitter, eventId, key,
					new NotificationDetailResponse(notification.getNotificationInfo()));
			}
		);
	}

	@Override
	public Slice<NotificationDetailResponse> getUsersNotifications(User user, Pageable pageable) {
		Slice<Notification> notifications = notificationRepository.findByReceiver(user, pageable);
		return notifications.map(notification ->
			new NotificationDetailResponse(notification.getNotificationInfo()));
	}

	@Override
	public NotificationSizeResponse getNotificationSize(User user) {
		long count = notificationRepository.countByReceiverAndIsRaed(user, false);
		return new NotificationSizeResponse(count);
	}

	@Override
	public void readNotification(Long notificationId, User user) {
		Notification notification = notificationRepository.findById(notificationId).orElseThrow(RuntimeException::new);
		notification.read();
	}

	@Override
	public void deleteNotification(Long senderId, Long receiverId, TargetType targetType, Long targetId) {
		Optional<Notification> notificationOpt = notificationRepository.findNotification(
			senderId, receiverId, targetType, targetId);

		if (notificationOpt.isEmpty()) {
			// 로그 남기기, 예외를 던지지 않고 그냥 반환
			log.warn("Notification not found for senderId: {}, receiverId: {}, targetType: {}, targetId: {}",
				senderId, receiverId, targetType, targetId);
			return;
		}

		Notification notification = notificationOpt.get();
		notificationRepository.delete(notification);
	}

	private Notification createNotification(User receiver, User sender, NotificationType notificationType,
		TargetType targetType, Long targetId, String content, String url) {
		return Notification.builder()
			.receiver(receiver)
			.sender(sender)
			.notificationType(notificationType)
			.targetType(targetType)
			.targetId(targetId)
			.content(content)
			.url(url)
			.build();
	}

}
