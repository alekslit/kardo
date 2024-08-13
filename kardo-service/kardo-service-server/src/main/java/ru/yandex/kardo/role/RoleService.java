package ru.yandex.kardo.role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    void deleteRole(Integer roleId);

    void getRoleById(Integer roleId);
}