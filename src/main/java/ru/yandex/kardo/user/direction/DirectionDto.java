package ru.yandex.kardo.user.direction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public final class DirectionDto {
    // идентификатор:
    private final Integer id;
    // название направления:
    private final String name;
}