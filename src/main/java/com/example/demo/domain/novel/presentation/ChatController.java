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

    private final RedisPublisher redisPublisher;
    private final ChannelTopic channelTopic;
    private final ChatRedisCacheService chatRedisCacheService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageRequest chatMessageRequest){

        ChatMessageSaveDto message = getChatMessageSaveDto(chatMessageRequest);

        if (message.getType() ==  MessageType.CHAT) {
            chatRedisCacheService.addChat(message);
        }else {
            chatRedisCacheService.addWrite(message);
        }

        redisPublisher.publish(channelTopic,message);

    }

    private static ChatMessageSaveDto getChatMessageSaveDto(ChatMessageRequest chatMessageRequest) {
        return ChatMessageSaveDto.builder()
                .type(chatMessageRequest.getMessageType())
                .message(chatMessageRequest.getMessage())
                .roomId(chatMessageRequest.getRoomId())
                .writer(chatMessageRequest.getUserName())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
                .userId(1L)
                .build();

    }


}