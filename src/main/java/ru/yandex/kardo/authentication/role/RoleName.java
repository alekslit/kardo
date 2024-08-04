package ru.yandex.kardo.authentication.role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleName implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    JUDGE("JUDGE"),
    GGG("GGG");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }
}