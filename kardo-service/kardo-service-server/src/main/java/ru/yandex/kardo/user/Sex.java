package ru.yandex.kardo.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sex {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String dtoSex;

    public static Sex stringToSex(String sex) {
        if (sex.equals(MALE.getDtoSex())) {
            return MALE;
        } else {
            return FEMALE;
        }
    }
}