package com.example.demo.domain.novel.service.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageSaveDto {

    public enum MessageType{
        ENTER,TALK,QUIT
    }

    private Long userId;
    private String roomId;
    private MessageType type;
    private String writer;
    private String profilePath;
    private String message;
    private String createdAt;


}