package ru.yandex.kardo.exception;

public class AlreadyExistException extends RuntimeException {
    private final String adviceToUser;

    public static final String DUPLICATE_USER_EMAIL_MESSAGE = "Пользователь с таким email уже существует. email = ";
    public static final String DUPLICATE_USER_EMAIL_ADVICE = "Пожалуйста, замените email.";

    public AlreadyExistException(String message, String adviceToUser) {
        super(message);
        this.adviceToUser = adviceToUser;
    }

    public String getAdviceToUser() {
        return adviceToUser;
    }
}