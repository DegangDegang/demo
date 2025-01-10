package com.example.demo.domain.credential.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken")
@Getter
public class RefreshTokenRedisEntity {

    @Id
    private String id;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long refreshTokenTtl;

    @Builder
    public RefreshTokenRedisEntity(String id, String refreshToken, Long refreshTokenTtl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.refreshTokenTtl = refreshTokenTtl;
    }
}