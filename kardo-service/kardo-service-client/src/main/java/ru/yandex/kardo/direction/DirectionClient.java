package ru.yandex.kardo.direction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.kardo.exception.NotFoundException;

import java.util.List;

import static ru.yandex.kardo.exception.NotFoundException.*;

@Service
@Slf4j
public class DirectionClient {
    private final RestTemplate rest;

    @Autowired
    public DirectionClient(@Value("${kardo-service.url}") String serverUrl, RestTemplateBuilder builder) {
        this.rest = builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .build();
    }

    public List<DirectionDto> getAllDirections() {
        log.debug("Отправляем запрос на получение списка всех направлений");
        final String path = "/directions";

        return rest.exchange(path,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DirectionDto>>() {
                }).getBody();
    }

    public void deleteDirection(Integer dirId) {
        log.debug("{} = {}", "Отправляем запрос на удаление направления c id", dirId);
        final String path = "/directions/" + dirId;
        try {
            rest.delete(path);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                        DIRECTION_ID_NOT_FOUND_MESSAGE, dirId);
                throw new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                        DIRECTION_NAME_NOT_FOUND_MESSAGE + dirId);
            }
        }
    }
}
