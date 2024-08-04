package ru.yandex.kardo.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.kardo.authentication.role.Role;
import ru.yandex.kardo.authentication.role.RoleName;
import ru.yandex.kardo.user.dto.NewUserRequest;
import ru.yandex.kardo.user.dto.NewUserResponse;

import java.time.LocalDateTime;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static User newUserRequestToUser(NewUserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .registrationDate(LocalDateTime.now())
                .roles(Collections.singleton(Role.builder()
                        .id(1)
                        .name(RoleName.USER)
                        .build()))
                .build();
    }

    public static NewUserResponse userToNewUserResponse(User user) {
        return NewUserResponse.builder()
                .email(user.getEmail())
                .build();
    }
}