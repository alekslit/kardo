package ru.yandex.kardo.user.direction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.kardo.exception.NotFoundException;

import java.util.List;

import static ru.yandex.kardo.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;

    /*--------------------Основные методы--------------------*/
    @Override
    public List<Direction> getAllDirections() {
        log.debug("{}.", "Попытка получить все объекты Direction");
        return directionRepository.findAll();
    }

    @Override
    public void deleteDirection(Integer dirId) {
        log.debug("{} = {}.", "Попытка удалить объект Direction по id", dirId);
        // проверим, существует ли такое направление:
        getDirectionById(dirId);
        directionRepository.deleteById(dirId);
    }

    /*--------------------Вспомогательные методы--------------------*/
    @Override
    public void getDirectionById(Integer dirId) {
        directionRepository.findById(dirId).orElseThrow(() -> {
            log.debug("{}: {}{}.", NotFoundException.class.getSimpleName(),
                    DIRECTION_ID_NOT_FOUND_MESSAGE, dirId);
            return new NotFoundException(NOT_FOUND_EXCEPTION_REASON,
                    DIRECTION_ID_NOT_FOUND_MESSAGE + dirId);
        });
    }
}