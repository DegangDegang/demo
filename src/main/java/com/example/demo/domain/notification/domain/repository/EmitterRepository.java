package com.example.demo.domain.notification.domain.repository;

import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

	SseEmitter save(String emitterId, SseEmitter sseEmitter);
	void saveEventCache(String eventCacheId, Object event);
	Map<String, SseEmitter> findAllEmitterStartWithByUserId(Long userId);
	Map<String, Object> findAllEventCacheStartWithByUserId(Long userId);
	void deleteById(String id);
	void deleteAllEmitterStartWithId(Long userId);
	void deleteAllEventCacheStartWithId(Long userId);
}
