package com.example.demo.domain.novel.presentation;



import com.example.demo.domain.novel.presentation.dto.ChatMessageRequest;
import com.example.demo.domain.novel.redis.pub.RedisPublisher;
import com.example.demo.domain.novel.service.ChatRedisCacheService;
import com.example.demo.domain.novel.service.MessageType;
import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {


    private final ChatRedisCacheService chatRedisCacheService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageRequest chatMessageRequest){
        chatRedisCacheService.publish(chatMessageRequest);
    }



}