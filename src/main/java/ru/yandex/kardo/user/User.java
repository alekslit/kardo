package ru.yandex.kardo.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // идентификатор пользователя:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // имя пользователя:
    @Column(name = "name")
    private String name;

    //электронная почта:
    @Column(name = "email")
    private String email;
}