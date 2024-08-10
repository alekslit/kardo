package ru.yandex.kardo.user.direction;

import java.util.List;

public interface DirectionService {
    List<Direction> getAllDirections();

    Direction deleteDirection(Integer dirId);

    Direction getDirectionById(Integer dirId);
}