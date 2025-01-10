package com.example.demo.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeUserInfoRequest {

    private String profilePath;
    private String nickname;
}
