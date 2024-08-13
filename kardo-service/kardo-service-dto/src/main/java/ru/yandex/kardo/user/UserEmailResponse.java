package ru.yandex.kardo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Setter
@Schema(description = "Email пользователя в ответ на удаление / создание пользователя")
public final class UserEmailResponse {
    // электронная почта:
    @Schema(description = "Электронная почта", example = "ivan@mail.ru")
    private String email;
}