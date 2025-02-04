package com.example.demo.domain.essay.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEssayRequest {

    private String title;

    private String content;

    private String imageUrl;
}
