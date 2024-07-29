package ru.yandex.kardo.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("ru.yandex.kardo")
@Slf4j
public class ErrorHandler {
    /*------Обработчики для статуса 400 (Bad request)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("Ошибка валидации данных из запроса.")
                .adviceToUser(e.getMessage())
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getMessage());

        return errorResponse;
    }
}