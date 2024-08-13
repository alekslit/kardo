package ru.yandex.kardo.user;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.kardo.direction.Direction;
import ru.yandex.kardo.role.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraphs({
        @NamedEntityGraph(name = "User.roles", attributeNodes = @NamedAttributeNode("roles")),
        @NamedEntityGraph(name = "User.direction", attributeNodes = @NamedAttributeNode("direction"))
})
public class User {
    // идентификатор пользователя:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // имя пользователя:
    @Column(name = "first_name")
    private String firstName;

    // фамилия пользователя:
    @Column(name = "last_name")
    private String lastName;

    // отчество пользователя:
    @Column(name = "patronymic")
    private String patronymic;

    // дата рождения пользователя:
    @Column(name = "birthday")
    private LocalDate birthday;

    // пол пользователя:
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    // электронная почта:
    @Column(name = "email")
    private String email;

    // номер телефона пользователя:
    @Column(name = "phone")
    private String phone;

    // ссылка на страницу пользователя в социальной сети:
    @Column(name = "social_link")
    private String socialLink;

    // страна проживания пользователя:
    @Column(name = "country")
    private String country;

    // регион проживания пользователя:
    @Column(name = "region")
    private String region;

    // город проживания пользователя:
    @Column(name = "city")
    private String city;

    // ссылка на портфолио пользователя:
    @Column(name = "portfolio_link")
    private String portfolioLink;

    // информация о пользователе / о себе / опыт и достижения:
    @Column(name = "about_user")
    private String aboutUser;

    // пароль от аккаунта пользователя:
    @Column(name = "password")
    private String password;

    // дата и время регистрации пользователя:
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    // роли пользователя (уровни доступа):
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // направление, которое интересно пользователю:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    // участник пользователь или нет:
    @Column(name = "participation")
    private Boolean participation;
}