package ru.yandex.kardo.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.kardo.exception.AlreadyExistException;
import ru.yandex.kardo.user.dto.NewUserRequest;

import java.util.Optional;

import static ru.yandex.kardo.exception.AlreadyExistException.ALREADY_EXIST_EXCEPTION_REASON;
import static ru.yandex.kardo.exception.AlreadyExistException.DUPLICATE_USER_EMAIL_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User saveUser(NewUserRequest userRequest) {
        log.debug("{} = {}.", "Попытка сохранить новый объект User с email", userRequest.getEmail());
        User user = UserMapper.newUserRequestToUser(userRequest);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.debug("{}: {}{}.", AlreadyExistException.class.getSimpleName(),
                    DUPLICATE_USER_EMAIL_MESSAGE, userRequest.getEmail());
            throw new AlreadyExistException(ALREADY_EXIST_EXCEPTION_REASON,
                    DUPLICATE_USER_EMAIL_MESSAGE + userRequest.getEmail());
        }

        return user;
    }

    // TODO проверочный метод - убрать:
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}