package ru.yandex.kardo.authentication.role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role deleteRole(Integer roleId);

    Role getRoleById(Integer roleId);
}