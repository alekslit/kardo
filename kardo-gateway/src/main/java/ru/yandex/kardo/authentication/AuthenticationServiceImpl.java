package ru.yandex.kardo.authentication;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.yandex.kardo.authentication.dto.AccessTokenResponse;
import ru.yandex.kardo.authentication.dto.JwtRequest;
import ru.yandex.kardo.authentication.dto.JwtResponseFullDto;
import ru.yandex.kardo.exception.AuthenticationException;
import ru.yandex.kardo.user.GenerateUserTokenDto;
import ru.yandex.kardo.user.UserClient;

import static ru.yandex.kardo.exception.AuthenticationException.AUTHENTICATION_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.AuthenticationException.USER_PASSWORD_INCORRECT_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserClient userClient;
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponseFullDto login(JwtRequest authenticationRequest) {
        log.debug("{} = {}.", "Попытка аутентификации пользователя с email", authenticationRequest.getEmail());
        final GenerateUserTokenDto user = userClient.getUserByEmail(authenticationRequest.getEmail());
        // проверка пароля пользователя:
        checkPassword(user, authenticationRequest);
        // токены:
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        return JwtUtils.generateJwtResponseFullDto(accessToken, refreshToken);
    }

    @Override
    public AccessTokenResponse getAccessToken(String refreshToken) {
        log.debug("{} = {}.", "Попытка получить новый accessToken по refreshToken", refreshToken);
        jwtProvider.validateRefreshToken(refreshToken);
        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String email = claims.getSubject();
        final GenerateUserTokenDto user = userClient.getUserByEmail(email);
        // токен:
        final String accessToken = jwtProvider.generateAccessToken(user);

        return JwtUtils.generateAccessTokenResponse(accessToken);
    }

    @Override
    public JwtResponseFullDto refreshTokens(String refreshToken) {
        log.debug("{} = {}.", "Попытка обновить оба токена по refreshToken", refreshToken);
        jwtProvider.validateRefreshToken(refreshToken);
        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String email = claims.getSubject();
        final GenerateUserTokenDto user = userClient.getUserByEmail(email);
        // токены:
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String newRefreshToken = jwtProvider.generateRefreshToken(user);

        return JwtUtils.generateJwtResponseFullDto(accessToken, newRefreshToken);
    }

    @Override
    public JwtAuthentication getAuthenticationInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    private void checkPassword(GenerateUserTokenDto user, JwtRequest authenticationRequest) {
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            log.debug("{}: {}.", AuthenticationException.class.getSimpleName(),
                    USER_PASSWORD_INCORRECT_MESSAGE);
            throw new AuthenticationException(AUTHENTICATION_EXCEPTION_REASON,
                    USER_PASSWORD_INCORRECT_MESSAGE);
        }
    }
}
