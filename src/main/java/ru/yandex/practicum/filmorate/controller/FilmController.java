package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@RestController
@Slf4j
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final Map<Integer, Film> films = new TreeMap<>();
    private final LocalDate dateCheck = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) throws ValidationException {
        releaseDateCheck(film.getReleaseDate());
        films.put(film.getId(), film);
        log.debug("Фильм: {} добавлен.", film);
        return film;
    }
    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws ValidationException {
        releaseDateCheck(film.getReleaseDate());

        if (!films.containsKey(film.getId()) || film.getId() < 1) {
            ValidationException e = new ValidationException("Film с id = " + film.getId() + " не существует");
            log.debug("Фильма нет в базе", e);
            throw e;
        }

        films.put(film.getId(), film);
        log.debug("Фильм: {} обновлён.", film);
        return film;
    }

    private void releaseDateCheck(LocalDate releaseDate) {
        if (releaseDate.isBefore(dateCheck)) {
            ValidationException e = new ValidationException("Дата релиза — раньше 28 декабря 1895 года");
            log.debug("Валидация не пройдена", e);
            throw e;
        }
    }

}
