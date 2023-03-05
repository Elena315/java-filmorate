package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {

    @Autowired
    FilmController filmController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addFilm_NormalTest() {

        Film film = new Film();
        film.setName("Big Fish");
        film.setDescription("Bla bla");
        film.setReleaseDate(LocalDate.of(2000,1,1));
        film.setDuration(140);
        film.setLikes(Stream.of(1, 2).collect(Collectors.toSet()));

        filmController.createFilm(film);
        assertEquals(film, filmController.getFilmById(1), "Фильм не попал в storage");
    }

    @Test
    void addFilm_NameValidationTest() {
        Film film = new Film();
        film.setName("Big Fish");
        film.setDescription("Bla bla");
        film.setReleaseDate(LocalDate.of(1895,12,26));
        film.setDuration(140);
        film.setLikes(Stream.of(1, 2).collect(Collectors.toSet()));

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
}