package ru.yandex.kardo.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public final class ErrorResponse {
    private final String status;
    private final String reason;
    private final String message;
    private final String timestamp;
}