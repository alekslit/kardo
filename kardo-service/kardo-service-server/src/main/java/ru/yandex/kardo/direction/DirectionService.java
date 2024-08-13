package ru.yandex.kardo.direction;

import java.util.List;

public interface DirectionService {
    List<Direction> getAllDirections();

    void deleteDirection(Integer dirId);

    void getDirectionById(Integer dirId);
}