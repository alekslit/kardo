package ru.yandex.kardo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Email пользователя в ответ на удаление / создание пользователя")
public final class UserEmailResponse {
    // электронная почта:
    @Schema(description = "Электронная почта", example = "ivan@mail.ru")
    private final String email;
}