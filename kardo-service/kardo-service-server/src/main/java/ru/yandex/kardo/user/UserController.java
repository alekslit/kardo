package ru.yandex.kardo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.kardo.direction.DirectionMapper;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;

    /*--------------------Методы с доступом PUBLIC--------------------*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserEmailResponse createUser(@RequestBody NewUserRequest userRequest) {
        return UserMapper.userToUserEmailResponse(userService.saveUser(userRequest));
    }

    @GetMapping("/community")
    public List<UserCommunityShortDto> getAllUserCommunity(
            @RequestParam(required = false) List<String> directions,
            @RequestParam(defaultValue = "false") Boolean participation,
            @RequestParam(required = false) List<String> countries,
            @RequestParam(required = false) List<String> cities,
            @RequestParam(required = false) String text,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return UserMapper.userToUserShortCommunity(userService
                .getAllUserCommunity(directions != null ?
                                DirectionMapper.stringToDirectionName(directions) : null,
                        participation, countries, cities, text, from, size));
    }

    @GetMapping("/community/{userId}")
    public UserCommunityFullDto getOtherUserById(@PathVariable Long userId) {
        return UserMapper.userToUserCommunityFullDto(userService.getUserById(userId));
    }

    /*--------------------Методы с доступом USER--------------------*/
    @GetMapping("/profile/{userId}")
    public UserProfileResponse getUserProfile(@PathVariable Long userId) {
        return UserMapper.userToUserProfileResponse(userService
                .getUserProfile(userId));
    }

    @GetMapping("/profile/settings/{userId}")
    public UserProfileSettingsResponse getUserProfileSettings(@PathVariable Long userId) {
        return UserMapper.userToUserProfileSettingsResponse(userService
                .getUserProfileSettings(userId));
    }

    @PatchMapping("/profile/settings/{userId}")
    public UserProfileSettingsResponse updateUser(
            @RequestBody UpdateUserRequest request,
            @PathVariable Long userId
    ) {
        return UserMapper.userToUserProfileSettingsResponse(userService
                .updateUser(request, userId));
    }

    /*--------------------Методы с доступом ADMIN--------------------*/
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserEmailResponse deleteUser(@PathVariable Long userId) {
        return UserMapper.userToUserEmailResponse(userService.deleteUser(userId));
    }

    /*--------------------Вспомогательные методы--------------------*/
    // метод для запроса данных пользователя, которые участвуют в формировании токенов:
    @GetMapping("/token")
    public GenerateUserTokenDto getUserByEmail(@RequestParam String email) {
        return UserMapper.userToGenerateUserDto(userService.findUserByEmail(email));
    }
}