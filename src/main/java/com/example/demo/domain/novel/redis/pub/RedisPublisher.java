package com.example.demo.domain.novel.redis.pub;

import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {

    //redis 발행

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageSaveDto messageDto) {

        redisTemplate.convertAndSend(topic.getTopic(), messageDto);
    }
}