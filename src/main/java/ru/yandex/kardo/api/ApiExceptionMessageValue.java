package ru.yandex.kardo.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiExceptionMessageValue {
    // getAllUserCommunity():
    public final static String GET_ALL_USER_COMMUNITY_400 = "{\n" +
            "    \"status\": \"400 BAD_REQUEST\",\n" +
            "    \"reason\": \"Ошибка валидации данных из запроса\",\n" +
            "    \"message\": \"getAllUserCommunity.from: Параметр запроса from, должен быть положительным числом или нулём.\",\n" +
            "    \"timestamp\": \"2024-08-10 04:32:51\"\n" +
            "}";

    // for All:
    public final static String NOT_VALID_USER_ID_400 = "{\n" +
            "    \"status\": \"400 BAD_REQUEST\",\n" +
            "    \"reason\": \"Ошибка валидации данных из запроса\",\n" +
            "    \"message\": \"getOtherUserById.userId: Параметр запроса userId, должен быть положительным числом.\",\n" +
            "    \"timestamp\": \"2024-08-10 04:56:50\"\n" +
            "}";
    public final static String NOT_VALID_EMAIL_400 = "{\n" +
            "    \"status\": \"400 BAD_REQUEST\",\n" +
            "    \"reason\": \"Ошибка валидации данных из запроса\",\n" +
            "    \"message\": \"Некорректный адрес электронной почты: ivanov.ru\",\n" +
            "    \"timestamp\": \"2024-08-10 01:32:27\"\n" +
            "}";
    public final static String NOT_USER_BY_EMAIL_401 = "{\n" +
            "    \"status\": \"401 UNAUTHORIZED\",\n" +
            "    \"reason\": \"Ошибка аутентификации\",\n" +
            "    \"message\": \"Пользователя с таким email не существует. email = ivanov@mail.ru\",\n" +
            "    \"timestamp\": \"2024-08-10 18:45:56\"\n" +
            "}";
    public final static String NOT_VALID_TOKEN_403 = "{\n" +
            "    \"status\": \"403 FORBIDDEN\",\n" +
            "    \"reason\": \"Ошибка валидации токена\",\n" +
            "    \"message\": \"Срок действия токена истёк\",\n" +
            "    \"timestamp\": \"2024-08-10 05:16:06\"\n" +
            "}";
    public final static String GET_USER_BY_ID_404 = "{\n" +
            "    \"status\": \"404 NOT_FOUND\",\n" +
            "    \"reason\": \"Объект не найден\",\n" +
            "    \"message\": \"Пользователя с таким id не существует. userId = 56\",\n" +
            "    \"timestamp\": \"2024-08-10 05:01:47\"\n" +
            "}";
    public final static String EMAIL_ALREADY_EXIST_409 = "{\n" +
            "    \"status\": \"409 CONFLICT\",\n" +
            "    \"reason\": \"Объект уже существует\",\n" +
            "    \"message\": \"Пользователь с таким email уже существует. email = ivanov@mail.ru\",\n" +
            "    \"timestamp\": \"2024-08-10 01:48:15\"\n" +
            "}";
    public final static String API_EXCEPTION_MESSAGE_500 = "{\n" +
            "    \"status\": \"500 INTERNAL_SERVER_ERROR\",\n" +
            "    \"reason\": \"Произошла непредвиденная ошибка\",\n" +
            "    \"message\": \"Пожалуйста, обратитесь в службу технической поддержки\",\n" +
            "    \"timestamp\": \"2024-08-10 01:33:59\"\n" +
            "}";
}