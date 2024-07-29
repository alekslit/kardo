package ru.yandex.kardo.user.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Builder
public final class NewUserResponse {
    // электронная почта:
    private final String email;
}