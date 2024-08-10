package ru.yandex.kardo.user;

import ru.yandex.kardo.user.direction.DirectionName;
import ru.yandex.kardo.user.dto.NewUserRequest;
import ru.yandex.kardo.user.dto.UpdateUserRequest;

import java.util.List;

public interface UserService {
    User saveUser(NewUserRequest userRequest);

    User findUserByEmail(String email);

    User getUserById(Long userId);

    User getUserProfile(Long userId);

    User getUserProfileSettings(Long userId);

    User updateUser(UpdateUserRequest request, Long userId);

/* TODO удалить:

    List<User> getAllUserCommunity(List<DirectionName> directions,
                                   Boolean participation,
                                   List<String> countries,
                                   List<String> cities,
                                   String text,
                                   Integer from,
                                   Integer size);*/

    List<User> getAllUserCommunity(List<DirectionName> directions,
                                   Boolean participation,
                                   List<String> countries,
                                   List<String> cities,
                                   String text,
                                   Integer from,
                                   Integer size);

    User deleteUser(Long userId);
}