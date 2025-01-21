package com.example.demo.domain.credential.service;

import com.example.demo.domain.credential.domain.RefreshTokenRedisEntity;
import com.example.demo.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import com.example.demo.domain.credential.exception.NotNullTokenException;
import com.example.demo.domain.credential.exception.RefreshTokenExpiredException;
import com.example.demo.domain.credential.exception.UserIdMismatchException;
import com.example.demo.domain.credential.presentation.dto.request.RegisterRequest;
import com.example.demo.domain.credential.presentation.dto.request.UnlinkRequest;
import com.example.demo.domain.credential.presentation.dto.response.*;
import com.example.demo.domain.user.domain.User;
import com.example.demo.domain.user.domain.repository.UserRepository;
import com.example.demo.global.api.dto.UserInfoToOauthDto;
import com.example.demo.global.exception.InvalidTokenException;
import com.example.demo.global.exception.UserNotFoundException;
import com.example.demo.global.security.JwtTokenProvider;
import com.example.demo.global.utils.user.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CredentialService {

    private final UserRepository userRepository;
    private final OauthFactory oauthFactory;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisEntityRepository refreshTokenRedisEntityRepository;
    private final UserUtils userUtils;

    public AuthTokensResponse testLogin(Long userId){
        User user = userUtils.getUserById(userId);
        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        String refreshToken = generateRefreshToken(userId);
        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    public void logout() {
        User user = userUtils.getUserFromSecurityContext();
        refreshTokenRedisEntityRepository.deleteById(user.getId().toString());
    }

    public void singUpTest(RegisterRequest registerRequest){
        User user =
                User.builder()
                        .oauthProvider(UUID.randomUUID().toString())
                        .oauthId(UUID.randomUUID().toString())
                        .email(null)
                        .profilePath(null)
                        .nickname(registerRequest.getNickname())
                        .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AfterOauthResponse getTokenToCode(OauthProvider oauthProvider, String code) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OauthTokenInfoDto oauthToken = oauthStrategy.getOauthToken(code);
        return new AfterOauthResponse(oauthToken.getIdToken(),oauthToken.getAccessToken());
    }

    @Transactional(readOnly = true)
    public CheckRegisteredResponse getUserAvailableRegister(String token, OauthProvider oauthProvider) {
        OauthStrategy oauthstrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthstrategy.getOIDCDecodePayload(token);
        Boolean isRegistered = !checkUserCanRegister(oidcDecodePayload, oauthProvider);
        return new CheckRegisteredResponse(isRegistered);
    }

    @Transactional(readOnly = true)
    public String getOauthLink(OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        return oauthStrategy.getOauthLink();
    }

    private Boolean checkUserCanRegister(
            OIDCDecodePayload oidcDecodePayload, OauthProvider oauthProvider) {
        Optional<User> user =
                userRepository.findByOauthIdAndOauthProvider(
                        oidcDecodePayload.getSub(), oauthProvider.getValue());
        return user.isEmpty();
    }

    public AuthTokensResponse loginUserByOCIDToken(String token, OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        User user =
                userRepository
                        .findByOauthIdAndOauthProvider(
                                oidcDecodePayload.getSub(), oauthProvider.getValue())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole());

        String refreshToken = generateRefreshToken(user.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokensResponse registerUserByOCIDToken(
            String token, RegisterRequest registerUserRequest, OauthProvider oauthProvider) {

        log.info("=== register [service]  ===");
        log.info("token={}", token);
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);

        log.info("getOuathStreadgy={}", oauthStrategy.getClass());
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        User user =
                User.builder()
                        .oauthProvider(oauthProvider.getValue())
                        .oauthId(oidcDecodePayload.getSub())
                        .email(oidcDecodePayload.getEmail())
                        .profilePath(registerUserRequest.getProfilePath())
                        .nickname(registerUserRequest.getNickname())
                        .build();
        userRepository.save(user);

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole());
        String refreshToken = generateRefreshToken(user.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokensResponse tokenRefresh(String requestRefreshToken) {

        log.info(requestRefreshToken);

        Optional<RefreshTokenRedisEntity> entityOptional =
                refreshTokenRedisEntityRepository.findByRefreshToken(requestRefreshToken);

        RefreshTokenRedisEntity refreshTokenRedisEntity =
                entityOptional.orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);

        Long userId = jwtTokenProvider.parseRefreshToken(requestRefreshToken);

        if (!userId.toString().equals(refreshTokenRedisEntity.getId())) {
            throw InvalidTokenException.EXCEPTION;
        }

        User user = userUtils.getUserById(userId);
        User loginUser = userUtils.getUserFromSecurityContext();

        if (user != loginUser) {
            throw UserNotFoundException.EXCEPTION;
        }

        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        String refreshToken = generateRefreshToken(userId);

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateRefreshToken(Long userId) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);
        Long tokenExpiredAt = jwtTokenProvider.getRefreshTokenTTlSecond();
        RefreshTokenRedisEntity build =
                RefreshTokenRedisEntity.builder()
                        .id(userId.toString())
                        .refreshTokenTtl(tokenExpiredAt)
                        .refreshToken(refreshToken)
                        .build();
        refreshTokenRedisEntityRepository.save(build);
        return refreshToken;
    }

    public void deleteUser(String oauthAccessToken) {
        User user = userUtils.getUserFromSecurityContext();
        OauthProvider provider = OauthProvider.valueOf(user.getOauthProvider().toUpperCase());
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(provider);
        String userOauthId = user.getOauthId();

        if(provider.equals(OauthProvider.GOOGLE)) {
            verifyUserOauthIdWithAccessToken(oauthAccessToken, userOauthId, oauthStrategy);
        }

        deleteUserData(user);

        UnlinkRequest unlinkRequest = createUnlinkRequest(provider, oauthAccessToken, userOauthId);
        oauthStrategy.unLink(unlinkRequest);
    }

    private void verifyUserOauthIdWithAccessToken(String oauthAccessToken, String oauthId, OauthStrategy oauthStrategy) {

        if(oauthAccessToken == null) {
            throw NotNullTokenException.EXCEPTION;
        }

        UserInfoToOauthDto userInfo = oauthStrategy.getUserInfo(oauthAccessToken);

        if (!userInfo.getId().equals(oauthId)) {
            throw UserIdMismatchException.EXCEPTION;
        }
    }

    private void deleteUserData(User user) {
        refreshTokenRedisEntityRepository.deleteById(user.getId().toString());
        userRepository.delete(user);
    }

    private UnlinkRequest createUnlinkRequest(OauthProvider provider, String oauthAccessToken, String oauthId) {

        if (provider.equals(OauthProvider.GOOGLE)) {
            return UnlinkRequest.createWithAccessToken(oauthAccessToken);
        } else {
            return UnlinkRequest.createWithOauthId(oauthId);
        }
    }

}

