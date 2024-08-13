package ru.yandex.kardo.direction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DirectionMapper {
    private static final String ILLEGAL_DIRECTION_NAME_MESSAGE = "Неверное название направления. name =";

    public static DirectionName stringToDirectionName(String name) {
        return Arrays.stream(DirectionName.values())
                .peek(System.out::println)
                .filter(direction -> direction.getDtoDirection().equals(name))
                .findFirst().orElseThrow(() -> {
                    log.debug("{}: {}{}.", IllegalArgumentException.class.getSimpleName(),
                            ILLEGAL_DIRECTION_NAME_MESSAGE, name);
                    return new IllegalArgumentException(ILLEGAL_DIRECTION_NAME_MESSAGE + name);
                });
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