package ru.yandex.kardo.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public final class RefreshJwtRequest {
    private final String refreshToken;
}