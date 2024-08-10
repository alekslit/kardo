package ru.yandex.kardo.authentication;

import ru.yandex.kardo.authentication.dto.AccessTokenResponse;
import ru.yandex.kardo.authentication.dto.JwtRequest;
import ru.yandex.kardo.authentication.dto.JwtResponseFullDto;

public interface AuthenticationService {
    JwtResponseFullDto login(JwtRequest authenticationRequest);

    AccessTokenResponse getAccessToken(String refreshToken);

    JwtResponseFullDto refreshTokens(String refreshToken);

    JwtAuthentication getAuthenticationInfo();
}