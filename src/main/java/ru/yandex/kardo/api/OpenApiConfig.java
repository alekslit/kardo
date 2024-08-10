package ru.yandex.kardo.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(title = "Kardo",
                description = "API спецификация MVP веб-приложения проекта КАРДО",
                version = "1.0.0"),
        tags = {
                @Tag(name = "PUBLIC: Пользователи",
                        description = "Публичный API для работы с пользователями"),
                @Tag(name = "USER: Пользователи",
                        description = "Приватный API для аутентифицированных пользователей c уровнем доступа USER"),
                @Tag(name = "ADMIN: Пользователи",
                        description = "API c уровнем доступа ADMIN для работы с пользователями"),
                @Tag(name = "PUBLIC: Аутентификация",
                        description = "API для аутентификации зарегистрированных пользователей")
        })
public class OpenApiConfig {
}