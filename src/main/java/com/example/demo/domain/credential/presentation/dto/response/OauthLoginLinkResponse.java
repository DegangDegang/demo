package com.example.demo.domain.credential.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthLoginLinkResponse {

    private String link;
}