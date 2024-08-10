package ru.yandex.kardo.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.kardo.authentication.dto.AccessTokenResponse;
import ru.yandex.kardo.authentication.dto.JwtRequest;
import ru.yandex.kardo.authentication.dto.JwtResponseFullDto;
import ru.yandex.kardo.authentication.dto.RefreshJwtRequest;
import ru.yandex.kardo.exception.ErrorResponse;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static ru.yandex.kardo.api.ApiExceptionMessageValue.*;

@RestController
@RequestMapping(path = "auth", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(
            tags = {"PUBLIC: Аутентификация"},
            summary = "Аутентификация пользователя",
            description = "Позволяет пройти аутентификацию пользователю"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")/*,
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
                            examples = {@ExampleObject(value = API_EXCEPTION_MESSAGE_500)}))*/
    })
    public JwtResponseFullDto login(@RequestBody JwtRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/token")
    public AccessTokenResponse getNewAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return authenticationService.getAccessToken(refreshJwtRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponseFullDto getNewRefreshToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return authenticationService.refreshTokens(refreshJwtRequest.getRefreshToken());
    }
}