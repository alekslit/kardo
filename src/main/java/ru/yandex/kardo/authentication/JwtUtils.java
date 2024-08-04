package ru.yandex.kardo.authentication;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.kardo.authentication.role.RoleName;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        return JwtAuthentication.builder()
                .email(claims.getSubject())
                .firstName(claims.get("firstName", String.class))
                .roles(getRoleNames(claims))
                .build();
    }

    private static Set<RoleName> getRoleNames(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(RoleName::valueOf)
                .collect(Collectors.toSet());
    }
}