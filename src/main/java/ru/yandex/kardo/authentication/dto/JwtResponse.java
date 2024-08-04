package ru.yandex.kardo.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public final class JwtResponse {
    private final String type = "Bearer";
    private final String accessToken;
    private final String refreshToken;
}