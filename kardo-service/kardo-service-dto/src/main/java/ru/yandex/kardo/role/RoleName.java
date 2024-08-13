package ru.yandex.kardo.role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleName implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    JUDGE("JUDGE");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}