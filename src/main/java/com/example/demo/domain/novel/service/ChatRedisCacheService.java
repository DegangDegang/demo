package com.example.demo.domain.novel.service;


import com.example.demo.domain.novel.presentation.dto.ChatMessageRequest;
import com.example.demo.domain.novel.presentation.dto.ChatResponse;
import com.example.demo.domain.novel.redis.pub.RedisPublisher;
import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRedisCacheService {

    public static final String CHAT_SORTED_SET_ = "CHAT_SORTED_SET_";
    public static final String WRITE_SORTED_SET_ = "WRITE_SORTED_SET_";
    public static final String NEW_CHAT = "NEW_CHAT";
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;
    private final RedisTemplate<String, String> roomRedisTemplate;
    private ZSetOperations<String, ChatMessageSaveDto> zSetOperations;

    private final RedisPublisher redisPublisher;
    private final ChannelTopic channelTopic;

    @PostConstruct
    private void init() {
        zSetOperations = chatRedisTemplate.opsForZSet();
    }

    public void publish(ChatMessageRequest chatMessageRequest) {

        ChatMessageSaveDto message = makeMessageDto(chatMessageRequest,1L);

        if (chatMessageRequest.getMessageType() == MessageType.CHAT) {
            addChat(message);
        }else {
            addWrite(message);
        }

        redisPublisher.publish(channelTopic,message);

    }

    //redis chat data 삽입
    public void addChat(ChatMessageSaveDto message) {
        redisTemplate.opsForZSet().add(CHAT_SORTED_SET_ + message.getRoomId(), message, changeLocalDateTimeToDouble(message.getCreatedAt()));
        redisTemplate.opsForZSet().add(NEW_CHAT, message, changeLocalDateTimeToDouble(message.getCreatedAt()));

    }

    public void addWrite(ChatMessageSaveDto message) {
        redisTemplate.opsForZSet().add(WRITE_SORTED_SET_ + message.getRoomId(), message, changeLocalDateTimeToDouble(message.getCreatedAt()));
    }

    //채팅 데이터 생성일자 Double 형으로 형변환
    public Double changeLocalDateTimeToDouble(String createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(createdAt, formatter);
        return ((Long) localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).doubleValue();
    }

    @Transactional
    public ChatResponse getChatsFromRedis(Long novelId) {
        Set<ChatMessageSaveDto> chatMessageSaveDtoSet = zSetOperations.reverseRange(CHAT_SORTED_SET_ + novelId, 0, -1);
        Set<ChatMessageSaveDto> s = zSetOperations.reverseRange(WRITE_SORTED_SET_ + novelId, 0, 0);
        List<ChatMessageSaveDto> chatMessageSaveDtos = new ArrayList<>(chatMessageSaveDtoSet);
        ChatMessageSaveDto next = s.iterator().next();

        return new ChatResponse(next.getMessage(), chatMessageSaveDtos);

    }

    private ChatResponse createChatResponse(String recentWriteMessage, List<ChatMessageSaveDto> chatMessages) {
        return new ChatResponse(recentWriteMessage, chatMessages);
    }

    private ChatMessageSaveDto makeMessageDto(ChatMessageRequest chatMessageRequest, Long userId) {
        return ChatMessageSaveDto.builder()
                .roomId(chatMessageRequest.getRoomId())
                .type(chatMessageRequest.getMessageType())
                .message(chatMessageRequest.getMessage())
                .userId(userId)
                // .createdAt(String.valueOf(LocalDateTime.now()))
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
                .build();
    }



}