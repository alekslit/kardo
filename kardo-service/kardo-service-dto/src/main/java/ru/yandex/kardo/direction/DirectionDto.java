package ru.yandex.kardo.direction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Направление конкурса")
public final class DirectionDto {
    // идентификатор:
    @Schema(description = "id направления", example = "1")
    private final Integer id;

    // название направления:
    @Schema(description = "Название направления", example = "BMX")
    private final String name;
}