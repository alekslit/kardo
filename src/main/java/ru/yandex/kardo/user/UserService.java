package ru.yandex.kardo.user;

import ru.yandex.kardo.user.dto.NewUserRequest;

public interface UserService {
    User saveUser(NewUserRequest userRequest);

    // TODO проверочный метод - убрать:
    User getUserById(Long userId);
}