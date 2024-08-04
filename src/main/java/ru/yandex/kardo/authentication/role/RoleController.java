package ru.yandex.kardo.authentication.role;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping()
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping()
    public Role saveRole() {
        return roleService.saveRole();
    }
}