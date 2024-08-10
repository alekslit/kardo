package ru.yandex.kardo.user.direction;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("direction")
@Validated
public class DirectionController {
    private final DirectionService directionService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<DirectionDto> getAllDirections() {
        return DirectionMapper.directionToDirectionDto(directionService.getAllDirections());
    }

    @DeleteMapping("{dirId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DirectionDto deleteDirection(
            @PathVariable @Positive(message = "Параметр запроса dirId, должен быть " +
                    "положительным числом.") Integer dirId
    ) {
        return DirectionMapper.directionToDirectionDto(directionService.deleteDirection(dirId));
    }
}