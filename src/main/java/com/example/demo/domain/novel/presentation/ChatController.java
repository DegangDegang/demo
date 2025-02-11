package com.example.demo.domain.novel.presentation;



import com.example.demo.domain.novel.presentation.dto.ChatMessageRequest;
import com.example.demo.domain.novel.redis.pub.RedisPublisher;
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

    private final RedisPublisher redisPublisher;
    private final ChannelTopic channelTopic;
    private final UserUtils userUtils;

    @MessageMapping("/chat/message")
    public void message(ChatMessageRequest chatMessageRequest){

        //User user = userUtils.getUserFromSecurityContext();


        ChatMessageSaveDto message = ChatMessageSaveDto.builder()
                .type(ChatMessageSaveDto.MessageType.TALK)
                .message(chatMessageRequest.getMessage())
                .profilePath("TEST")
                .roomId(chatMessageRequest.getRoomId())
                .writer(chatMessageRequest.getUserName())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
                .userId(1L)
                .roomId(chatMessageRequest.getRoomId())
                .build();

        redisPublisher.publish(channelTopic,message);
        //chatRedisCacheService.addChat(message);
    }


}