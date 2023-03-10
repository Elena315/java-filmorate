package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
   public FilmService(FilmStorage filmStorage, UserStorage userStorage){
       this.filmStorage = filmStorage;
       this.userStorage = userStorage;
   }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film delete(Long filmId){
        return filmStorage.delete(filmId);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new FilmNotFoundException("Фильм c ID=" + filmId + " не найден!");
        }

        if (userStorage.getUserById(userId) == null) {
            throw new UserNotFoundException("Пользователь c ID=" + userId + " не найден!");
        }

        film.getLikes().add(userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new FilmNotFoundException("Фильм c ID=" + filmId + " не найден!");
        }

        if (!film.getLikes().contains(userId)) {
            throw new UserNotFoundException("Лайк от пользователя c ID=" + userId + " не найден!");
        }

        film.getLikes().remove(userId);
    }

    public List<Film> getPopular(Integer count) {
        return filmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}