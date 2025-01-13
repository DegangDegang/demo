package com.example.demo.domain.post.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritePostRequest {
    private String title;
    private String content;
}
