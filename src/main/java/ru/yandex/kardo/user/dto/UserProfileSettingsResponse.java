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
public final class UserProfileSettingsResponse {
    // полное имя пользователя(имя + фамилия):
    @Schema(description = "Полное имя пользователя (имя + фамилия)", example = "Иван Иванов")
    private final String fullName;

    // имя пользователя:
    @Schema(description = "Имя пользователя", example = "Иван")
    private final String firstName;

    // фамилия пользователя:
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private final String lastName;

    // отчество пользователя:
    @Schema(description = "Отчество пользователя", example = "Иванович")
    private final String patronymic;

    // дата рождения пользователя (отдаём в формате: dd.MM.yyyy):
    @Schema(description = "Дата рождения пользователя", example = "27.03.1995")
    private final String birthday;

    // пол пользователя:
    @Schema(description = "Пол пользователя", example = "Женский")
    private final String sex;

    // электронная почта:
    @Schema(description = "Электронная почта", example = "ivan@mail.ru")
    private final String email;

    // номер телефона пользователя (формат: 8 800 555 35 35):
    @Schema(description = "Номер телефона пользователя", example = "8 800 555 55 35")
    private final String phone;

    // ссылка на страницу пользователя в социальной сети:
    @Schema(description = "Ссылка на страницу в социальной сети", example = "https://rugram.ru/ivan")
    private final String socialLink;

    // страна проживания пользователя:
    @Schema(description = "Страна проживания пользователя", example = "Россия")
    private final String country;

    // регион проживания пользователя:
    @Schema(description = "Регион проживания пользователя", example = "ЯНАО")
    private final String region;

    // город проживания пользователя:
    @Schema(description = "Город проживания пользователя", example = "Москва")
    private final String city;

    // ссылка на портфолио пользователя:
    @Schema(description = "Ссылка на портфолио пользователя", example = "https://portfolio.ru/ivan")
    private final String portfolioLink;

    // информация о пользователе / о себе / опыт и достижения:
    @Schema(description = "Информация о пользователе / о себе / опыт и достижения",
            example = "Люблю котов, прогулки и BMX.")
    private final String aboutUser;

    // направление, которое интересно пользователю:
    @Schema(description = "Направление, которое интересно пользователю", example = "BMX")
    private final String direction;

    // участник пользователь или нет:
    @Schema(description = "Участник пользователь или нет", example = "false")
    private final Boolean participation;
}