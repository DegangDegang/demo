package com.example.demo.domain.notification.domain.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

	@Override
	public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
		emitters.put(emitterId, sseEmitter);
		return sseEmitter;
	}

	@Override
	public void saveEventCache(String eventCacheId, Object event) {
		eventCache.put(eventCacheId, event);
	}

	@Override
	public Map<String, SseEmitter> findAllEmitterStartWithByUserId(Long userId) {
		return emitters.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(Long.toString(userId)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public Map<String, Object> findAllEventCacheStartWithByUserId(Long userId) {
		return eventCache.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(Long.toString(userId)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));	}

	@Override
	public void deleteById(String id) {
		emitters.remove(id);
	}

	@Override
	public void deleteAllEmitterStartWithId(Long userId) {
		emitters.forEach(
			(key, emitter) -> {
				if (key.startsWith(Long.toString(userId))) {
					emitters.remove(key);
				}
			}
		);
	}

	@Override
	public void deleteAllEventCacheStartWithId(Long userId) {
		eventCache.forEach(
			(key, emitter) -> {
				if (key.startsWith(Long.toString(userId))) {
					eventCache.remove(key);
				}
			}
		);
	}
}
