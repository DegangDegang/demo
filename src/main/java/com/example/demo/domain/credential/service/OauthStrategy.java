package com.example.demo.domain.credential.service;

import com.example.demo.domain.credential.presentation.dto.request.UnlinkRequest;
import com.example.demo.domain.credential.presentation.dto.response.OauthTokenInfoDto;
import com.example.demo.global.api.dto.UserInfoToOauthDto;

public interface OauthStrategy {
    OIDCDecodePayload getOIDCDecodePayload(String token);

    String getOauthLink();

    OauthTokenInfoDto getOauthToken(String code);

    UserInfoToOauthDto getUserInfo(String oauthAccessToken);

    void unLink(UnlinkRequest unlinkRequest);
}
