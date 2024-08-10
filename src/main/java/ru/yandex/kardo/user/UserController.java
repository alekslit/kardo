package ru.yandex.kardo.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.kardo.authentication.AuthenticationService;
import ru.yandex.kardo.exception.ErrorResponse;
import ru.yandex.kardo.user.direction.DirectionMapper;
import ru.yandex.kardo.user.dto.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.yandex.kardo.api.ApiExceptionMessageValue.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    /*--------------------Методы с доступом PUBLIC--------------------*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            tags = {"PUBLIC: Пользователи"},
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_EMAIL_400)})),
            @ApiResponse(responseCode = "409",
                    description = "Conflict",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = EMAIL_ALREADY_EXIST_409)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public UserEmailResponse createUser(
            @Valid
            @RequestBody
            @Parameter(required = true) NewUserRequest userRequest
    ) {
        return UserMapper.userToUserEmailResponse(userService.saveUser(userRequest));
    }

    @GetMapping("/community")
    @Operation(
            tags = {"PUBLIC: Пользователи"},
            summary = "Получение списка пользователей",
            description = "Позволяет получить список пользователей комьюнити. " +
                    "Есть возможность фильтрации по параметрам"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = GET_ALL_USER_COMMUNITY_400)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public List<UserCommunityShortDto> getAllUserCommunity(
            @RequestParam(required = false)
            @Parameter(description = "Фильтр по направлениям") List<String> directions,
            @RequestParam(defaultValue = "false")
            @Parameter(description = "Фильтр по участникам") Boolean participation,
            @RequestParam(required = false)
            @Parameter(description = "Фильтр по странам") List<String> countries,
            @RequestParam(required = false)
            @Parameter(description = "Фильтр по городам") List<String> cities,
            @RequestParam(required = false)
            @Parameter(description = "Фильтр по имени пользователя") String text,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = "Параметр запроса from, должен быть " +
                    "положительным числом или нулём.")
            @Parameter(description = "Количество элементов, которые нужно " +
                    "пропустить для формирования текущего набора", required = true) Integer from,
            @RequestParam(defaultValue = "10")
            @Positive(message = "Параметр запроса size, должен быть " +
                    "положительным числом.")
            @Parameter(description = "Количество элементов в наборе", required = true) Integer size
    ) {
        return UserMapper.userToUserShortCommunity(userService
                .getAllUserCommunity(directions != null ?
                                DirectionMapper.stringToDirectionName(directions) : null,
                        participation, countries, cities, text, from, size));
    }

    @GetMapping("/community/{userId}")
    @Operation(
            tags = {"PUBLIC: Пользователи"},
            summary = "Получение пользователя",
            description = "Позволяет получить данные одного из пользователей комьюнити"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_USER_ID_400)})),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = USER_NOT_FOUND_404)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public UserCommunityFullDto getOtherUserById(
            @PathVariable
            @Positive(message = "Параметр запроса userId, должен быть положительным числом.")
            @Parameter(description = "id пользователя", required = true) Long userId
    ) {
        return UserMapper.userToUserCommunityFullDto(userService.getUserById(userId));
    }

    /*--------------------Методы с доступом USER--------------------*/
    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            tags = {"USER: Пользователи"},
            summary = "Получение профиля пользователя",
            description = "Позволяет получить данные профиля пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_TOKEN_403)})),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = USER_NOT_FOUND_404)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public UserProfileResponse getUserProfile() {
        return UserMapper.userToUserProfileResponse(userService
                .getUserProfile(authenticationService.getAuthenticationInfo().getId()));
    }

    @GetMapping("/profile/settings")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            tags = {"USER: Пользователи"},
            summary = "Получение настроек профиля пользователя",
            description = "Позволяет получить текущие данные настроек профиля пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_TOKEN_403)})),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = USER_NOT_FOUND_404)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public UserProfileSettingsResponse getUserProfileSettings() {
        return UserMapper.userToUserProfileSettingsResponse(userService
                .getUserProfileSettings(authenticationService.getAuthenticationInfo().getId()));
    }

    @PatchMapping("/profile/settings")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            tags = {"USER: Пользователи"},
            summary = "Обновление настроек пользователя",
            description = "Позволяет обновить данные профиля пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_EMAIL_400)})),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_TOKEN_403)})),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = USER_NOT_FOUND_404)})),
            @ApiResponse(responseCode = "409",
                    description = "Conflict",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = EMAIL_ALREADY_EXIST_409)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public UserProfileSettingsResponse updateUser(
            @Valid @RequestBody
            @Parameter(required = true) UpdateUserRequest request
    ) {
        return UserMapper.userToUserProfileSettingsResponse(userService
                .updateUser(request, authenticationService.getAuthenticationInfo().getId()));
    }

    /*--------------------Методы с доступом ADMIN--------------------*/
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            tags = {"ADMIN: Пользователи"},
            summary = "Удаление пользователя",
            description = "Позволяет удалить пользователя из БД"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_USER_ID_400)})),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = NOT_VALID_TOKEN_403)})),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = USER_NOT_FOUND_404)})),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))
    })
    public UserEmailResponse deleteUser(
            @PathVariable
            @Positive(message = "Параметр запроса userId, должен быть положительным числом.")
            @Parameter(description = "id пользователя", required = true) Long userId
    ) {
        return UserMapper.userToUserEmailResponse(userService.deleteUser(userId));
    }
}