package ru.yandex.kardo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.yandex.kardo.user.direction.DirectionName;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @EntityGraph(value = "User.roles")
    Optional<User> findByEmail(String email);

/* TODO delete:

    @EntityGraph(value = "User.direction")
    @Query("SELECT u " +
            "FROM User AS u " +
            "WHERE (1 = 1) " +
            "  AND (COALESCE(:directions) IS NULL OR u.direction.name IN :directions) " +
            "  AND (:participation IS NULL OR u.participation = :participation) " +
            "  AND (COALESCE(:countries) IS NULL OR u.country IN :countries) " +
            "  AND (COALESCE(:cities) IS NULL OR u.city IN :cities) " +
            "  AND (:text IS NULL OR u.firstName ILIKE CONCAT('%', CAST(:text AS char), '%'))")
    Page<User> findAllUsersCommunity(List<DirectionName> directions,
                                     Boolean participation,
                                     List<String> countries,
                                     List<String> cities,
                                     String text,
                                     Pageable pageable);*/
}