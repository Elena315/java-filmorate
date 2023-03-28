package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getAllFilms();

    Film create(Film film);

    Film update(Film film);

    Film delete(Long filmId);

    Film getFilmById(Long filmId);
}