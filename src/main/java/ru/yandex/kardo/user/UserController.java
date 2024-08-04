package ru.yandex.kardo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.kardo.user.dto.NewUserRequest;
import ru.yandex.kardo.user.dto.NewUserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users")
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponse createUser(@Valid @RequestBody NewUserRequest userRequest) {
        return UserMapper.userToNewUserResponse(userService.saveUser(userRequest));
    }

    // TODO проверочный метод - убрать:
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}