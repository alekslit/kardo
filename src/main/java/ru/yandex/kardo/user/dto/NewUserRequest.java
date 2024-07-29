package ru.yandex.kardo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public final class NewUserRequest {
    // имя пользователя:
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 50, message = "Длинна имени должна быть от {min} до {max} символов.")
    private final String firstName;

    // электронная почта:
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Size(min = 6, max = 254, message = "Длинна email должна быть от {min} до {max} символов.")
    @Email(message = "Некорректный адрес электронной почты: ${validatedValue}")
    private final String email;

    // пароль от аккаунта пользователя:
    @NotBlank(message = "Пароль пользователя не может быть пустым")
    @Size(min = 8, max = 50, message = "Пароль должен содержать от {min} до {max} символов.")
    private final String password;
}