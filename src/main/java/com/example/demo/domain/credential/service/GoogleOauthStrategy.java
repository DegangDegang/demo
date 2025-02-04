package com.example.demo.domain.credential.service;

import com.example.demo.domain.credential.exception.NotNullTokenException;
import com.example.demo.domain.credential.presentation.dto.request.UnlinkRequest;
import com.example.demo.domain.credential.presentation.dto.response.OauthTokenInfoDto;
import com.example.demo.global.api.client.GoogleAuthClient;
import com.example.demo.global.api.client.GoogleUnlinkClient;
import com.example.demo.global.api.dto.OIDCKeysResponse;
import com.example.demo.global.api.dto.OauthTokenResponse;
import com.example.demo.global.api.dto.UserInfoToOauthDto;
import com.example.demo.global.property.OauthProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@AllArgsConstructor
@Component("GOOGLE")
public class GoogleOauthStrategy implements OauthStrategy{

    private final OauthProperties oauthProperties;
    private final GoogleAuthClient googleAuthClient;
    private final OauthOIDCProvider oauthOIDCProvider;
    private final GoogleUnlinkClient googleUnlinkClient;
    private static final String PREFIX = "Bearer ";
    private static final String ISSUER = "https://accounts.google.com";
    private static final String QUERY_STRING =
            "/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";
    private static final String scope = "https://www.googleapis.com/auth/userinfo.email";

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token){
        OIDCKeysResponse oidcGoogleKeysResponse = googleAuthClient.getGoogleOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(token,ISSUER,oauthProperties.getGoogleAppId(),oidcGoogleKeysResponse);
    }

    @Override
    public String getOauthLink() {

        return oauthProperties.getGoogleBaseUrl()
                + String.format(
                QUERY_STRING,
                oauthProperties.getGoogleAppId(),
                oauthProperties.getGoogleRedirectUrl(),
                scope);
    }

    @Override
    public OauthTokenInfoDto getOauthToken(String code) {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        OauthTokenResponse oauthTokenResponse = googleAuthClient
                .googleAuth(
                        oauthProperties.getGoogleAppId(),
                        oauthProperties.getGoogleRedirectUrl(),
                        decodedCode,
                        oauthProperties.getGoogleClientSecret());
       return OauthTokenInfoDto.builder()
               .idToken(oauthTokenResponse.getIdToken())
               .accessToken(oauthTokenResponse.getAccessToken())
               .build();

    }

    @Override
    public UserInfoToOauthDto getUserInfo(String accessToken){
         return googleAuthClient.getGoogleInfo(PREFIX + accessToken);
    }

    @Override
    public void unLink(UnlinkRequest unlinkRequest) {
        if (unlinkRequest.getAccessToken() == null) {
            throw NotNullTokenException.EXCEPTION;
        }
        googleUnlinkClient.unlink(unlinkRequest.getAccessToken());

    }

}
