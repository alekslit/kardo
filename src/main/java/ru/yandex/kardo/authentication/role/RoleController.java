package ru.yandex.kardo.authentication.role;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
@Validated
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RoleDto> getAllRoles() {
        return RoleMapper.roleToRoleDto(roleService.getAllRoles());
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RoleDto deleteRole(
            @PathVariable @Positive(message = "Параметр запроса roleId, должен быть " +
                    "положительным числом.") Integer roleId
    ) {
        return RoleMapper.roleToRoleDto(roleService.deleteRole(roleId));
    }
}