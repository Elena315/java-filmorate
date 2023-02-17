package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired(required = false)
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms() {
        List<Film> filmsList = new ArrayList<>(filmService.getAllFilms().values());
        log.debug("Поступил GET запрос. Количество фильмов: {}", filmsList.size());
        return filmsList;

    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        filmService.validateReleaseDate(film, "добавлен");
        log.debug("Поступил POST запрос. Фильм: {} сохранён.", film.getName());
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        filmService.validateReleaseDate(film, "обновлен");
        log.debug("Поступил PUT запрос. Фильм: {} обновлён.", film.getName());
        return filmService.updateFilm(film);
    }


}
