package ru.yandex.kardo.direction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "directions", produces = APPLICATION_JSON_VALUE)
public class DirectionController {
    private final DirectionService directionService;

    @GetMapping
    public List<DirectionDto> getAllDirections() {
        return DirectionMapper.directionToDirectionDto(directionService.getAllDirections());
    }

    @DeleteMapping("/{dirId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDirection(Integer dirId) {
        directionService.deleteDirection(dirId);
    }
}