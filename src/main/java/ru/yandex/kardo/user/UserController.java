package ru.yandex.kardo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}