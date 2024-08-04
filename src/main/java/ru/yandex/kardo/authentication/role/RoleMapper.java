package ru.yandex.kardo.authentication.role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleMapper {
    public static RoleName roleToRoleName(Role role) {
        return role.getName();
    }

    public static Set<RoleName> roleToRoleName(Set<Role> roles) {
        return roles.stream()
                .map(RoleMapper::roleToRoleName)
                .collect(Collectors.toSet());
    }
}