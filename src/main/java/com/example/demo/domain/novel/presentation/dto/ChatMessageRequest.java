package com.example.demo.domain.novel.presentation.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageRequest {

    private String roomId;
    private String message;
    private String userName;

}
