package ru.yandex.kardo.authentication;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.yandex.kardo.authentication.dto.JwtRequest;
import ru.yandex.kardo.authentication.dto.JwtResponse;
import ru.yandex.kardo.exception.AuthenticationException;
import ru.yandex.kardo.user.User;
import ru.yandex.kardo.user.UserService;

import static ru.yandex.kardo.exception.AuthenticationException.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(JwtRequest authenticationRequest) {
        final User user = getUserByEmail(authenticationRequest.getEmail());
        // проверка пароля пользователя:
        checkPassword(user, authenticationRequest);
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtResponse getAccessToken(String refreshToken) {
        jwtProvider.validateRefreshToken(refreshToken);
        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String email = claims.getSubject();
        final User user = getUserByEmail(email);

        final String accessToken = jwtProvider.generateAccessToken(user);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public JwtResponse refreshTokens(String refreshToken) {
        jwtProvider.validateRefreshToken(refreshToken);
        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String email = claims.getSubject();
        final User user = getUserByEmail(email);

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String newRefreshToken = jwtProvider.generateRefreshToken(user);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public JwtAuthentication getAuthenticationInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    private void checkPassword(User user, JwtRequest authenticationRequest) {
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            log.debug("{}: {}.", AuthenticationException.class.getSimpleName(),
                    USER_PASSWORD_INCORRECT_MESSAGE);
            throw new AuthenticationException(AUTHENTICATION_EXCEPTION_REASON,
                    USER_PASSWORD_INCORRECT_MESSAGE);
        }
    }

    private User getUserByEmail(String email) {
        return userService.findByEmail(email).orElseThrow(() -> {
            log.debug("{}: {}{}.", AuthenticationException.class.getSimpleName(),
                    USER_EMAIL_NOT_FOUND_MESSAGE, email);
            return new AuthenticationException(AUTHENTICATION_EXCEPTION_REASON,
                    USER_EMAIL_NOT_FOUND_MESSAGE + email);
        });
    }
}
