package ru.yandex.kardo.authentication.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public final class RoleDto {
    // идентификатор:
    private final Integer id;
    // название роли:
    private final String name;
}