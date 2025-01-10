package com.example.demo.domain.credential.presentation.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    private String nickname;

    private String profilePath;

}
