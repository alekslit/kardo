package ru.yandex.kardo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Данные о конкретном пользователе из комьюнити")
public final class UserCommunityFullDto {
    // идентификатор пользователя:
    @Schema(description = "id пользователя", example = "7")
    private final Long id;

    // полное имя пользователя(имя + фамилия):
    @Schema(description = "Полное имя пользователя (имя + фамилия)", example = "Иван Иванов")
    private final String fullName;

    // страна + город проживания:
    @Schema(description = "Страна + город проживания", example = "Россия, Москва")
    private final String countryAndCity;

    // направление конкурса и статус "Участник", при наличии:
    @Schema(description = "направление конкурса и статус Участник, при наличии",
            example = "BMX [Участник]")
    private final String directionAndStatus;

    // информация о пользователе / о себе / опыт и достижения:
    @Schema(description = "Информация о пользователе / о себе / опыт и достижения",
            example = "Люблю котов, прогулки и BMX.")
    private final String aboutUser;
}