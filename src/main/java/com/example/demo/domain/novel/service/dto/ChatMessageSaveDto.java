package com.example.demo.domain.novel.service.dto;

import com.example.demo.domain.novel.service.MessageType;
import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageSaveDto {

    private Long userId;
    private String roomId;
    private MessageType type;
    private String writer;
    private String profilePath;
    private String message;
    private String createdAt;

    @Builder
    public ChatMessageSaveDto(Long userId, String roomId, MessageType type, String writer, String profilePath, String message, String createdAt) {
        this.userId = userId;
        this.roomId = roomId;
        this.type = type;
        this.writer = writer;
        this.profilePath = profilePath;
        this.message = message;
        this.createdAt = createdAt;
    }

//    @Builder
//    public static ChatMessageSaveDto createChatMessageSaveDto(ChatMessageSaveDto saveDto){
//        return ChatMessageSaveDto.builder()
//                .type(MessageType.CHAT)
//                .userId(saveDto.getUserId())
//                .roomId(saveDto.getRoomId())
//                .writer(saveDto.getWriter())
//                .createdAt(saveDto.getCreatedAt())
//                .message(saveDto.getMessage())
//                .build();
//    }
}