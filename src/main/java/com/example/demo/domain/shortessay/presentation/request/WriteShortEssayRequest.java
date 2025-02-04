package com.example.demo.domain.shortessay.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WriteShortEssayRequest {

    private String content;

    private String imgUrl;
}
