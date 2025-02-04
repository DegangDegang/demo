package com.example.demo.domain.credential.presentation.dto.request;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnlinkRequest {
    private String accessToken;
}