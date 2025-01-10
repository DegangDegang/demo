package com.example.demo.domain.credential.presentation.dto.request;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnlinkRequest {
    private String accessToken;
    private String oauthId;

    public static UnlinkRequest createWithAccessToken(String accessToken) {
        return UnlinkRequest.builder().accessToken(accessToken).build();
    }

    public static UnlinkRequest createWithOauthId(String oauthId) {
        return UnlinkRequest.builder().oauthId(oauthId).build();
    }
}