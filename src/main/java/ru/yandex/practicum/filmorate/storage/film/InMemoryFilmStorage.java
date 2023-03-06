package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Long id;

    private Map<Long, Film> films;

    public InMemoryFilmStorage() {
        this.id = 0L;
        this.films = new HashMap<>();
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        if (isValidFilm(film)) {
            film.setId(++id);
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException("Фильм с ID=" + film.getId() + " не найден!");
        }
        if (isValidFilm(film)) {
            films.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film delete(Long filmId) {
        if (filmId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        return films.remove(filmId);
    }

    @Override
    public Film getFilmById(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException("Фильм с ID=" + filmId + " не найден!");
        }
        return films.get(filmId);
    }

    private boolean isValidFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не должно быть пустым!");
        }
        if ((film.getDescription().length()) > 200 || (film.getDescription().isEmpty())) {
            throw new ValidationException("Описание фильма больше 200 символов или пустое: " + film.getDescription().length());
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Некорректная дата релиза фильма: " + film.getReleaseDate());
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность должна быть положительной: " + film.getDuration());
        }
        return true;
    }
}

