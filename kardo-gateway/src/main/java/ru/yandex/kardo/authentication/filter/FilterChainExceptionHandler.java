package ru.yandex.kardo.authentication.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.yandex.kardo.exception.ErrorResponse;
import ru.yandex.kardo.exception.JwtValidationException;
import ru.yandex.kardo.util.DateMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtValidationException e) {
            log.debug("{}: {} ({})", FilterChainExceptionHandler.class.getSimpleName(),
                    e.getMessage(), e.getResponseMessage());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.FORBIDDEN.toString())
                    .reason(e.getMessage())
                    .message(e.getResponseMessage())
                    .timestamp(DateMapper.getExceptionTimestampString(LocalDateTime.now()))
                    .build();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(convertToJson(errorResponse));
        }
    }

    private String convertToJson(ErrorResponse errorResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(errorResponse);
    }
}