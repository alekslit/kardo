package ru.yandex.kardo.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Schema(description = "Запрос на обновление Refresh и Access токенов")
public final class RefreshJwtRequest {
    @Schema(description = "Refresh токен", example = "(многочисленный набор символов)")
    private String refreshToken;
}