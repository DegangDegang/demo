package com.example.demo.domain.shortessay.presentation.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WriteShortEssayRequest {

    private String content;

    private String imgUrl;

    private List<String> keywords;

    private List<String> currentKeywords;
}
