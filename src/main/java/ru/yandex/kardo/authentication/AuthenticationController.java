package ru.yandex.kardo.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.kardo.authentication.dto.JwtRequest;
import ru.yandex.kardo.authentication.dto.JwtResponse;
import ru.yandex.kardo.authentication.dto.RefreshJwtRequest;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/token")
    public JwtResponse getNewAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return authenticationService.getAccessToken(refreshJwtRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponse getNewRefreshToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        return authenticationService.refreshTokens(refreshJwtRequest.getRefreshToken());
    }
}