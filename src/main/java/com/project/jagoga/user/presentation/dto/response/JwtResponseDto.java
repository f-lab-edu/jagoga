package com.project.jagoga.user.presentation.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtResponseDto {

    private final String accessToken;

    public static JwtResponseDto createInstance(String accessToken) {
        return new JwtResponseDto(accessToken);
    }
}
