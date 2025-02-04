package com.example.demo.domain.credential.presentation;

import com.example.demo.domain.credential.presentation.dto.request.OauthCodeRequest;
import com.example.demo.domain.credential.presentation.dto.request.RegisterRequest;
import com.example.demo.domain.credential.presentation.dto.request.TokenRefreshRequest;
import com.example.demo.domain.credential.presentation.dto.response.*;
import com.example.demo.domain.credential.service.CredentialService;
import com.example.demo.domain.credential.service.OauthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
@Slf4j
public class CredentialController {

    private final CredentialService credentialService;

    @PostMapping("/test-login/{userId}")
    public AuthTokensResponse loginTest(@PathVariable("userId") Long userId){
        return credentialService.testLogin(userId);
    }

    @PostMapping("/sign-up-test")
    public void signUptTest(@RequestBody RegisterRequest registerRequest){
        credentialService.singUpTest(registerRequest);
    }

    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }

    @GetMapping("/oauth/kakao")
    public AfterOauthResponse kakaoAuth(OauthCodeRequest oauthCodeRequest) {
        log.info("code = {}",oauthCodeRequest.getCode());
        return credentialService.getTokenToCode(OauthProvider.KAKAO, oauthCodeRequest.getCode());
    }

    @GetMapping("/oauth/link/google")
    public OauthLoginLinkResponse getGoogleOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.GOOGLE));
    }

    @GetMapping("/oauth/google")
    public AfterOauthResponse googleAuth(OauthCodeRequest oauthCodeRequest) {
        log.info("code = {}",oauthCodeRequest.getCode());
        return credentialService.getTokenToCode(OauthProvider.GOOGLE, oauthCodeRequest.getCode());
    }

    @GetMapping("/oauth/valid/register")
    public CheckRegisteredResponse valid(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) {
        log.info("controller token = {}",token);
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

    @PostMapping("/login")
    public AuthTokensResponse loginUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider) {
        return credentialService.loginUserByOCIDToken(token, oauthProvider);
    }

    @PostMapping
    public AuthTokensResponse registerUser(
            @RequestParam("idToken") String token,
            @RequestParam("provider") OauthProvider oauthProvider,
            @RequestBody RegisterRequest registerRequest) {

        log.info("=========== register api start ============");
        log.info("[controller] register token = {}",token);
        return credentialService.registerUserByOCIDToken(token, registerRequest, oauthProvider);
    }

    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(
            @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return credentialService.tokenRefresh(tokenRefreshRequest.getRefreshToken());
    }

    @PostMapping("/logout")
    public void logout() {
        credentialService.logout();
    }

    @DeleteMapping
    public void deleteUser(@RequestParam(value = "oauth_access_token", required = false) String token) {
        credentialService.deleteUser(token);
    }

}
