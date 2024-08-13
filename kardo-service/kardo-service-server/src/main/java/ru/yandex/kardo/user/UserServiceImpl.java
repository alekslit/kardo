package ru.yandex.kardo.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.yandex.kardo.direction.Direction;
import ru.yandex.kardo.direction.DirectionMapper;
import ru.yandex.kardo.direction.DirectionName;
import ru.yandex.kardo.direction.DirectionRepository;
import ru.yandex.kardo.exception.AlreadyExistException;
import ru.yandex.kardo.exception.AuthenticationException;
import ru.yandex.kardo.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.kardo.exception.AlreadyExistException.ALREADY_EXIST_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.AlreadyExistException.DUPLICATE_USER_EMAIL_MESSAGE;
import static ru.yandex.kardo.exception.AuthenticationException.AUTHENTICATION_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.AuthenticationException.USER_EMAIL_NOT_FOUND_MESSAGE;
import static ru.yandex.kardo.exception.NotFoundException.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DirectionRepository directionRepository;

    /*--------------------Основные методы--------------------*/
    @Override
    public User saveUser(NewUserRequest userRequest) {
        log.debug("{} = {}.", "Попытка сохранить новый объект User с email", userRequest.getEmail());
        User user = UserMapper.newUserRequestToUser(userRequest);

        return saveUserObject(user);
    }

    @Override
    public User getUserProfile(Long userId) {
        log.debug("{} = {}.", "Попытка получить информацию для профиля пользователя с id", userId);
        return getUserById(userId);
    }

    @Override
    public User getUserProfileSettings(Long userId) {
        log.debug("{} = {}.", "Попытка получить информацию для настроек профиля пользователя с id", userId);
        return getUserById(userId);
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        log.debug("{} = {}.", "Попытка обновить объект User c id", userId);
        // нашли пользователя:
        User userFromDb = getUserById(userId);
        // нашли направление:
        Direction direction = request.getDirection() != null ?
                getDirectionByName(DirectionMapper.stringToDirectionName(request.getDirection())) : null;
        // обновляем объект:
        updateUserObject(request, userFromDb, direction);

        // сохраняем в БД:
        return saveUserObject(userFromDb);
    }

    @Override
    public List<User> getAllUserCommunity(List<DirectionName> directions,
                                          Boolean participation,
                                          List<String> countries,
                                          List<String> cities,
                                          String text,
                                          Integer from,
                                          Integer size) {
        log.debug("{}.", "Попытка получить все объекты User");
        // пагинация:
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        // фильтры поиска:
        List<BooleanExpression> conditions = getUserCommunityFilter(directions,
                participation, countries, cities, text);
        if (!conditions.isEmpty()) {
            BooleanExpression finalCondition = conditions.stream()
                    .reduce(BooleanExpression::and)
                    .get();
            return userRepository.findAll(finalCondition, pageRequest).getContent();
        }

        return userRepository.findAll(pageRequest).getContent();
    }

    @Override
    public User deleteUser(Long userId) {
        log.debug("{} = {}.", "Попытка удалить объект User по id", userId);
        // проверим, существует ли такой пользователь:
        User user = getUserById(userId);
        userRepository.deleteById(userId);

        return user;
    }

    /*--------------------Вспомогательные методы--------------------*/
    private List<BooleanExpression> getUserCommunityFilter(List<DirectionName> directions,
                                                           Boolean participation,
                                                           List<String> countries,
                                                           List<String> cities,
                                                           String text) {
        QUser user = QUser.user;
        List<BooleanExpression> conditions = new ArrayList<>();
        if (directions != null) {
            conditions.add(user.direction.name.in(directions));
        }
        if (participation) {
            conditions.add(user.participation.eq(participation));
        }
        if (countries != null) {
            conditions.add(user.country.in(countries));
        }
        if (cities != null) {
            conditions.add(user.city.in(cities));
        }
        if (text != null) {
            text = String.format("%s%s%s", "%", text.toLowerCase(), "%");
            conditions.add(user.firstName.likeIgnoreCase(text));
        }

        return conditions;
    }

    @Override
    public User getUserById(Long userId) {
        log.debug("{} = {}.", "Попытка получить объект User по id", userId);
        return userRepository.findById(userId).orElseThrow(() -> {
            log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                    USER_NOT_FOUND_MESSAGE, userId);
            return new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                    USER_NOT_FOUND_MESSAGE + userId);
        });
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.debug("{}: {}{}.", AuthenticationException.class.getSimpleName(),
                    USER_EMAIL_NOT_FOUND_MESSAGE, email);
            return new AuthenticationException(AUTHENTICATION_EXCEPTION_REASON,
                    USER_EMAIL_NOT_FOUND_MESSAGE + email);
        });
    }

    private Direction getDirectionByName(DirectionName name) {
        return directionRepository.getByName(name).orElseThrow(() -> {
            log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                    DIRECTION_NAME_NOT_FOUND_MESSAGE, name);
            return new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                    DIRECTION_NAME_NOT_FOUND_MESSAGE + name);
        });
    }

    private void updateUserObject(UpdateUserRequest request, User user, Direction direction) {
        // firstName:
        user.setFirstName(request.getFirstName() != null ?
                request.getFirstName() : user.getFirstName());
        // lastName:
        user.setLastName(request.getLastName() != null ?
                request.getLastName() : user.getLastName());
        // patronymic:
        user.setPatronymic(request.getPatronymic() != null ?
                request.getPatronymic() : user.getPatronymic());
        // birthday:
        user.setBirthday(request.getBirthday() != null ?
                request.getBirthday() : user.getBirthday());
        // sex:
        user.setSex(request.getSex() != null ?
                Sex.stringToSex(request.getSex()) : user.getSex());
        // email:
        user.setEmail(request.getEmail() != null ?
                request.getEmail() : user.getEmail());
        // phone:
        user.setPhone(request.getPhone() != null ?
                request.getPhone() : user.getPhone());
        // socialLink:
        user.setSocialLink(request.getSocialLink() != null ?
                request.getSocialLink() : user.getSocialLink());
        // country:
        user.setCountry(request.getCountry() != null ?
                request.getCountry() : user.getCountry());
        // region:
        user.setRegion(request.getRegion() != null ?
                request.getRegion() : user.getRegion());
        // city:
        user.setCity(request.getCity() != null ?
                request.getCity() : user.getCity());
        // portfolioLink:
        user.setPortfolioLink(request.getPortfolioLink() != null ?
                request.getPortfolioLink() : user.getPortfolioLink());
        // aboutUser:
        user.setAboutUser(request.getAboutUser() != null ?
                request.getAboutUser() : user.getAboutUser());
        // direction:
        user.setDirection(direction != null ?
                direction : user.getDirection());
        // participation:
        user.setParticipation(request.getParticipation() != null ?
                request.getParticipation() : user.getParticipation());
    }

    private User saveUserObject(User user) {
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.debug("{}: {}{}.", AlreadyExistException.class.getSimpleName(),
                    DUPLICATE_USER_EMAIL_MESSAGE, user.getEmail());
            throw new AlreadyExistException(ALREADY_EXIST_EXCEPTION_REASON,
                    DUPLICATE_USER_EMAIL_MESSAGE + user.getEmail());
        }

        return user;
    }
}