package ru.yandex.kardo.user.direction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DirectionName {
    BMX("BMX"),
    KICK_SCOOTERING("Трюковой самокат"),
    HIP_HOP("Хип-хоп"),
    PARKOUR("Паркур"),
    FREE_RUNNING("Фриран"),
    TRICKING("Трикинг"),
    BREAKING("Брейнкинг"),
    WORKOUT("Воркаут"),
    GRAFFITI("Граффити"),
    SKATEBOARDING("Скейтбординг"),
    DJING("Диджеинг");

    private final String dtoDirection;
}