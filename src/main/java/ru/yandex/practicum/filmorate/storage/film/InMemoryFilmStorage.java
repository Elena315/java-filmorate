package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Long id;

    private Map<Long, Film> films;

    public InMemoryFilmStorage() {
        this.id = 0L;
        this.films = new HashMap<>();
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        film.setId(++id);
        return films.put(film.getId(), film);
    }

    @Override
    public Film update(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException("Фильм с ID=" + film.getId() + " не найден!");
        }
        return films.put(film.getId(), film);
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
}

