package ru.yandex.kardo.user;

import ru.yandex.kardo.user.dto.NewUserRequest;

import java.util.Optional;

public interface UserService {
    User saveUser(NewUserRequest userRequest);

    // TODO проверочный метод - убрать:
    User getUserById(Long userId);

    Optional<User> findByEmail(String email);
}