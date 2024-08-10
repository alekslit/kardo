package ru.yandex.kardo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Данные пользователя в его профиле")
public final class UserProfileResponse {
    // полное имя пользователя(имя + фамилия):
    @Schema(description = "Полное имя пользователя (имя + фамилия)", example = "Иван Иванов")
    private final String fullName;

    // страна + город проживания:
    @Schema(description = "Страна + город проживания", example = "Россия, Москва")
    private final String countryAndCity;

    // направление конкурса и статус "Участник", при наличии:
    @Schema(description = "Информация о пользователе / о себе / опыт и достижения",
            example = "Люблю котов, прогулки и BMX.")
    private final String directionAndStatus;
}