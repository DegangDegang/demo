package com.example.demo.domain.novel.presentation;

import com.example.demo.domain.novel.presentation.dto.ChatMessageRequest;
import com.example.demo.domain.novel.service.ChatRedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatRedisCacheService chatRedisCacheService;

    @MessageMapping("/chat/message")
    public void message(
            @Header("Authorization") String token,
            @Payload ChatMessageRequest chatMessageRequest){

        log.info("token {}", token);
        log.info("chatMessageRequest {}", chatMessageRequest.getMessage());
        log.info("chatMessageRequest {}", chatMessageRequest.getMessage());

        chatRedisCacheService.publish(token, chatMessageRequest);
    }


}