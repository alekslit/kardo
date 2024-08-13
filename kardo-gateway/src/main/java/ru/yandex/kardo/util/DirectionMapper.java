package ru.yandex.kardo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.kardo.direction.DirectionName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DirectionMapper {
    private static final String ILLEGAL_DIRECTION_NAME_MESSAGE = "Неверное название направления. name =";

    public static DirectionName stringToDirectionName(String name) {
        return Arrays.stream(DirectionName.values())
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

    public static String directionNameToString(DirectionName directionName) {
        return directionName.getDtoDirection();
    }

    public static List<String> directionNameToString(List<DirectionName> directionName) {
        return directionName.stream()
                .map(DirectionMapper::directionNameToString)
                .collect(Collectors.toList());
    }
}