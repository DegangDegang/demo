package com.example.demo.domain.novel.presentation.dto;

import com.example.demo.domain.essay.presentation.dto.response.HostInfoDto;
import com.example.demo.domain.novel.service.dto.ChatMessageSaveDto;
import lombok.Getter;
import java.util.List;

@Getter
public class ChatResponse {

    private String content;

    private List<ChatMessageSaveDto> messages;


    public ChatResponse(String content, List<ChatMessageSaveDto> messages) {
        this.content = content;
        this.messages = messages;
    }
}
