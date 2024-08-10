package ru.yandex.kardo.user.direction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DirectionMapper {
    private static final String ILLEGAL_DIRECTION_NAME_MESSAGE = "Неверное название направления. name =";

    public static DirectionName stringToDirectionName(String name) {
        try {
            return DirectionName.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.debug("{}: {}{}.", e.getClass().getSimpleName(),
                    ILLEGAL_DIRECTION_NAME_MESSAGE, name);
            throw  new IllegalArgumentException(ILLEGAL_DIRECTION_NAME_MESSAGE + name);
        }
    }

    public static List<DirectionName> stringToDirectionName(List<String> directions) {
        return directions.stream()
                .map(DirectionMapper::stringToDirectionName)
                .collect(Collectors.toList());
    }

    public static DirectionDto directionToDirectionDto(Direction direction) {
        return DirectionDto.builder()
                .id(direction.getId())
                .name(direction.getName().toString())
                .build();
    }

    public static List<DirectionDto> directionToDirectionDto(List<Direction> directions) {
        return directions.stream()
                .map(DirectionMapper::directionToDirectionDto)
                .collect(Collectors.toList());
    }
}