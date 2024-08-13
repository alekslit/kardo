package ru.yandex.kardo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.kardo.direction.DirectionName;
import ru.yandex.kardo.exception.AlreadyExistException;
import ru.yandex.kardo.exception.AuthenticationException;
import ru.yandex.kardo.exception.NotFoundException;

import java.util.List;

import static ru.yandex.kardo.exception.AlreadyExistException.ALREADY_EXIST_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.AlreadyExistException.DUPLICATE_USER_EMAIL_MESSAGE;
import static ru.yandex.kardo.exception.AuthenticationException.AUTHENTICATION_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.AuthenticationException.USER_EMAIL_NOT_FOUND_MESSAGE;
import static ru.yandex.kardo.exception.NotFoundException.NOT_FOUND_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.NotFoundException.USER_NOT_FOUND_MESSAGE;

@Service
@Slf4j
public class UserClient {
    private final RestTemplate restTemplate;
    private final RestClient restClient;

    private static final String ILLEGAL_DIRECTION_NAME_MESSAGE = "Неверное название направления. name =";

    @Autowired
    public UserClient(@Value("${kardo-service.url}") String serverUrl, RestTemplateBuilder builder) {
        this.restTemplate = builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .build();
        this.restClient = RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory())
                .baseUrl(serverUrl)
                .build();
    }

    /*--------------------Основные методы--------------------*/
    public GenerateUserTokenDto getUserByEmail(String email) {
        log.debug("{} = {}", "Отправляем запрос на получение данных о пользователе с email", email);
        final String path = "/users/token?email={email}";
        ResponseEntity<GenerateUserTokenDto> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.exchange(path,
                    HttpMethod.GET,
                    null,
                    GenerateUserTokenDto.class,
                    email);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                log.debug("{}: {}{}.", AuthenticationException.class.getSimpleName(),
                        USER_EMAIL_NOT_FOUND_MESSAGE, email);
                throw new AuthenticationException(AUTHENTICATION_EXCEPTION_REASON,
                        USER_EMAIL_NOT_FOUND_MESSAGE + email);
            }
        }

        return response.getBody();
    }

    public UserEmailResponse createUser(NewUserRequest request) {
        log.debug("{} = {}", "Отправляем запрос на создание пользователя с email", request.getEmail());
        final String path = "/users";
        ResponseEntity<UserEmailResponse> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.postForEntity(path, request, UserEmailResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 409) {
                log.debug("{}: {}{}.", AlreadyExistException.class.getSimpleName(),
                        DUPLICATE_USER_EMAIL_MESSAGE, request.getEmail());
                throw new AlreadyExistException(ALREADY_EXIST_EXCEPTION_REASON,
                        DUPLICATE_USER_EMAIL_MESSAGE + request.getEmail());
            }
        }

        return response.getBody();
    }

    public List<UserCommunityShortDto> getAllUserCommunity(List<DirectionName> directions,
                                                           Boolean participation,
                                                           List<String> countries,
                                                           List<String> cities,
                                                           String text,
                                                           Integer from,
                                                           Integer size) {
        log.debug("Отправляем запрос на получение пользователей комьюнити с фильтрами");
        final String path = "/users/community?directions={directions}&participation={participation}" +
                "&countries={countries}&cities={cities}&text={text}&from={from}&size={size}";
        ResponseEntity<List<UserCommunityShortDto>> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    },
                    listToString(directionToString(directions)),
                    participation,
                    listToString(countries),
                    listToString(cities),
                    text,
                    from,
                    size);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                log.debug("{}: {}{}.", IllegalArgumentException.class.getSimpleName(),
                        ILLEGAL_DIRECTION_NAME_MESSAGE, directions);
                throw new IllegalArgumentException(ILLEGAL_DIRECTION_NAME_MESSAGE + directions);
            }
        }

        return response.getBody();
    }

    public UserCommunityFullDto getOtherUserById(Long userId) {
        log.debug("{} = {}", "Отправляем запрос на получение данных о пользователе c id", userId);
        final String path = "/users/community/" + userId;
        ResponseEntity<UserCommunityFullDto> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.getForEntity(path, UserCommunityFullDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                        USER_NOT_FOUND_MESSAGE, userId);
                throw new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                        USER_NOT_FOUND_MESSAGE + userId);
            }
        }

        return response.getBody();
    }

    public UserProfileResponse getUserProfile(Long userId) {
        log.debug("{} = {}", "Отправляем запрос на получение информации о профиле пользователя c id", userId);
        final String path = "/users/profile/" + userId;
        ResponseEntity<UserProfileResponse> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.getForEntity(path, UserProfileResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                        USER_NOT_FOUND_MESSAGE, userId);
                throw new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                        USER_NOT_FOUND_MESSAGE + userId);
            }
        }

        return response.getBody();
    }

    public UserProfileSettingsResponse getUserProfileSettings(Long userId) {
        log.debug("{} = {}", "Отправляем запрос на получение информации о настройках " +
                "профиля пользователя c id", userId);
        final String path = "/users/profile/settings/" + userId;
        ResponseEntity<UserProfileSettingsResponse> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.getForEntity(path, UserProfileSettingsResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                        USER_NOT_FOUND_MESSAGE, userId);
                throw new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                        USER_NOT_FOUND_MESSAGE + userId);
            }
        }

        return response.getBody();
    }

    public UserProfileSettingsResponse updateUser(UpdateUserRequest request, Long userId) {
        log.debug("{} = {}", "Отправляем запрос на изменение настроек профиля пользователя c id", userId);
        final String path = "/users/profile/settings/" + userId;
        try {
            return restClient.patch()
                    .uri(path)
                    .body(request)
                    .retrieve()
                    .body(UserProfileSettingsResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                log.debug("{}: {}{}.", IllegalArgumentException.class.getSimpleName(),
                        ILLEGAL_DIRECTION_NAME_MESSAGE, request.getDirection());
                throw new IllegalArgumentException(ILLEGAL_DIRECTION_NAME_MESSAGE + request.getDirection());
            }
            if (e.getStatusCode().value() == 404) {
                log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                        "Пользователь или направление указаны некорректно. userId = ", userId);
                throw new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                        "Пользователь или направление указаны некорректно. userId = " + userId);
            }
        }

        return null;
    }

    public UserEmailResponse deleteUser(Long userId) {
        log.debug("{} = {}", "Отправляем запрос на удаление пользователя c id", userId);
        final String path = "/users/" + userId;
        ResponseEntity<UserEmailResponse> response =
                new ResponseEntity<>(HttpStatusCode.valueOf(200));
        try {
            response = restTemplate.exchange(path, HttpMethod.DELETE, null, UserEmailResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                        "Пользователь или направление указаны некорректно. userId = ", userId);
                throw new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                        "Пользователь или направление указаны некорректно. userId = " + userId);
            }
        }

        return response.getBody();
    }

    /*--------------------Вспомогательные методы--------------------*/

    private String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder stringToSend = new StringBuilder();
        stringToSend.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            stringToSend.append(",").append(list.get(i));
        }

        return stringToSend.toString();
    }

    private List<String> directionToString(List<DirectionName> directions) {
        if (directions == null) {
            return null;
        }

        return directions.stream()
                .map(DirectionName::getDtoDirection)
                .toList();
    }
}
