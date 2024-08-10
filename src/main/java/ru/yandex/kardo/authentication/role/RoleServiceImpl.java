package ru.yandex.kardo.authentication.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.kardo.exception.NotFoundException;
import ru.yandex.kardo.user.User;

import java.util.List;

import static ru.yandex.kardo.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /*--------------------Основные методы--------------------*/
    @Override
    public List<Role> getAllRoles() {
        log.debug("{}.", "Попытка получить все объекты Role");
        return roleRepository.findAll();
    }

    @Override
    public Role deleteRole(Integer roleId) {
        log.debug("{} = {}.", "Попытка удалить объект Role по id", roleId);
        // проверим, существует ли такая роль:
        Role role = getRoleById(roleId);
        roleRepository.deleteById(roleId);

        return role;
    }

    /*--------------------Вспомогательные методы--------------------*/
    @Override
    public Role getRoleById(Integer roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> {
            log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                    ROLE_NOT_FOUND_MESSAGE, roleId);
            return new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                    ROLE_NOT_FOUND_MESSAGE + roleId);
        });
    }
}