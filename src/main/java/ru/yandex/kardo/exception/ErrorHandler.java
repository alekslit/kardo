package ru.yandex.kardo.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.kardo.authentication.filter.FilterChainExceptionHandler;
import ru.yandex.kardo.util.DateMapper;

import java.time.LocalDateTime;

@RestControllerAdvice("ru.yandex.kardo")
@Slf4j
public class ErrorHandler {
    /*------Обработчики для статуса 400 (Bad request)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Ошибка валидации данных из запроса")
                .message(e.getMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getMessage());

        return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Недопустимый аргумент метода")
                .message(e.getMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getMessage());

        return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Ошибка валидации данных из запроса")
                .message(e.getFieldError().getDefaultMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getFieldError().getDefaultMessage());

        return errorResponse;
    }

    /*------Обработчики для статуса 401 (Unauthorized)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleConstraintAuthenticationException(final AuthenticationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.toString())
                .reason(e.getMessage())
                .message(e.getResponseMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getResponseMessage());

        return errorResponse;
    }

    /*------Обработчики для статуса 403 (Forbidden)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleJwtValidationException(final JwtValidationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.toString())
                .reason(e.getMessage())
                .message(e.getResponseMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {} ({})", e.getClass().getSimpleName(), e.getMessage(), e.getResponseMessage());

        return errorResponse;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(final AccessDeniedException e) {
        final String responseMessage = "Недостаточно прав доступа для просмотра ресурса";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.toString())
                .reason("Ошибка доступа")
                .message(responseMessage)
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), responseMessage);

        return errorResponse;
    }

    /*------Обработчики для статуса 404 (Not found)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .reason(e.getMessage())
                .message(e.getResponseMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getMessage());

        return errorResponse;
    }

    /*------Обработчики для статуса 409 (Conflict)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistException(final AlreadyExistException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.toString())
                .reason(e.getMessage())
                .message(e.getResponseMessage())
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("{}: {}", e.getClass().getSimpleName(), e.getResponseMessage());

        return errorResponse;
    }

    /*------Обработчики для статуса 500 (Internal server error)------*/
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason("Произошла непредвиденная ошибка")
                .message("Пожалуйста, обратитесь в службу технической поддержки")
                .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                .build();
        log.debug("500 {}: {}", e.getClass().getSimpleName(), e.getMessage(), e);

        return errorResponse;
    }
}