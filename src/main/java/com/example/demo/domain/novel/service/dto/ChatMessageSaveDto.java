package com.example.demo.domain.novel.service.dto;

import com.example.demo.domain.novel.service.MessageType;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageSaveDto {

    private Long userId;
    private String roomId;
    private MessageType type;
    private String writer;
    private String profilePath;
    private String message;
    private String createdAt;

    public static ChatMessageSaveDto createChatMessageSaveDto(ChatMessageSaveDto saveDto){
        return ChatMessageSaveDto.builder()
                .type(MessageType.CHAT)
                .userId(saveDto.getUserId())
                .roomId(saveDto.getRoomId())
                .writer(saveDto.getWriter())
                .createdAt(saveDto.getCreatedAt())
                .message(saveDto.getMessage())
                .build();
    }
}