package ru.yandex.kardo.authentication;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.kardo.authentication.dto.AccessTokenResponse;
import ru.yandex.kardo.authentication.dto.JwtResponseFullDto;
import ru.yandex.kardo.role.RoleName;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    /*--------------------Основные методы--------------------*/
    public static JwtAuthentication generateJwtAuthentication(Claims claims) {
        return JwtAuthentication.builder()
                .email(claims.getSubject())
                .id(claims.get("id", Long.class))
                .roles(getRoleNames(claims))
                .build();
    }

    public static JwtResponseFullDto generateJwtResponseFullDto(String accessToken, String refreshToken) {
        return JwtResponseFullDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static AccessTokenResponse generateAccessTokenResponse(String accessToken) {
        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    /*--------------------Вспомогательные методы--------------------*/
    private static Set<RoleName> getRoleNames(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(RoleName::valueOf)
                .collect(Collectors.toSet());
    }
}