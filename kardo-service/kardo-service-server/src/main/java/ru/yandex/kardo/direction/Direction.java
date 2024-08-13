package ru.yandex.kardo.direction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "directions", schema = "public")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direction {
    // идентификатор:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direction_id")
    private Integer id;

    // название направления:
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private DirectionName name;
}