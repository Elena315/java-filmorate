package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@AllArgsConstructor
public class FilmService {

    private static int increment = 0;

    private final Validator validator;

    private static final LocalDate START_DATA = LocalDate.of(1895, 12, 28);

    private final FilmStorage filmStorage;

    public Map<Integer, Film> getAllFilms() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        validate(film);
        validateReleaseDate(film, "добавлен");
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        validate(film);
        return filmStorage.update(film);
    }

    public void validateReleaseDate(Film film, String text) {
        if (film.getReleaseDate().isBefore(START_DATA)) {
            log.debug("Не {} фильм: {}", text, film.getName());
            throw new ValidationException("Дата релиза не может быть раньше " + START_DATA);
        }
        log.debug("{} фильм: {}", text, film.getName());
    }
    private void validate(Film film) {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (!violations.isEmpty()) {
            throw new ValidationException("Ошибка валидации Фильма");
        }
        if (film.getId() == 0) {
            film.setId(getNextId());
        }
    }

    private static int getNextId() {
        return ++increment;
    }
}