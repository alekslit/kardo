package ru.yandex.kardo.user.direction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    Optional<Direction> getByName(DirectionName name);
}