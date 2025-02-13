package com.example.demo.domain.novel.service;


import com.example.demo.domain.novel.presentation.dto.ChatResponse;
import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRedisCacheService {

    public static final String CHAT_SORTED_SET_ = "CHAT_SORTED_SET_";
    public static final String WRITE_SORTED_SET_ = "WRITE_SORTED_SET_";
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;
    private final RedisTemplate<String, String> roomRedisTemplate;
    private ZSetOperations<String, ChatMessageSaveDto> zSetOperations;

    @PostConstruct
    private void init() {
        zSetOperations = chatRedisTemplate.opsForZSet();
    }

    //redis chat data 삽입
    public void addChat(ChatMessageSaveDto chatMessageSaveDto) {
        ChatMessageSaveDto savedData = ChatMessageSaveDto.createChatMessageSaveDto(chatMessageSaveDto);
        redisTemplate.opsForZSet().add(CHAT_SORTED_SET_ + savedData.getRoomId(), savedData, changeLocalDateTimeToDouble(savedData.getCreatedAt()));
    }

    public void addWrite(ChatMessageSaveDto chatMessageSaveDto) {
        ChatMessageSaveDto savedData = ChatMessageSaveDto.createChatMessageSaveDto(chatMessageSaveDto);
        redisTemplate.opsForZSet().add(WRITE_SORTED_SET_ + savedData.getRoomId(), savedData, changeLocalDateTimeToDouble(savedData.getCreatedAt()));
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

        return new ChatResponse(next.getMessage(),chatMessageSaveDtos);

    }



}